package com.travel.travel_booking_service.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.travel.travel_booking_service.dto.request.AuthenticationRequest;
import com.travel.travel_booking_service.dto.request.IntrospectRequest;
import com.travel.travel_booking_service.dto.request.LogoutRequest;
import com.travel.travel_booking_service.dto.request.RefreshRequest;
import com.travel.travel_booking_service.dto.response.AuthenticationResponse;
import com.travel.travel_booking_service.dto.response.IntrospectResponse;
import com.travel.travel_booking_service.dto.response.LoginResponse;
import com.travel.travel_booking_service.entity.InvalidatedToken;
import com.travel.travel_booking_service.entity.RolePermission;
import com.travel.travel_booking_service.entity.User;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.repository.InvalidatedTokenRepository;
import com.travel.travel_booking_service.repository.UserRepository;
import com.travel.travel_booking_service.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESH_DURATION;

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        SignedJWT signToken = verifyRefreshToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        // Kiem tra hieu luc cua token
        var signedJWT = verifyRefreshToken(request.getToken());

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();
        var user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Tạo JWT token moi
        String token = generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Tạo JWT token
        String token = generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid = false;
        String username = null;
        String role;
        List<String> permissions = new ArrayList<>();

        try {
            // Xác thực token và lấy thông tin claim
            SignedJWT jwtToken = verifyToken(token);
            JWTClaimsSet claims = jwtToken.getJWTClaimsSet();

            // Lấy username từ claim "sub"
            username = claims.getSubject();

            // Lấy scope: ví dụ "ROLE_ADMIN UPDATE_CATEGORY"
            String scope = (String) claims.getClaim("scope");
            List<String> scopes = Arrays.asList(scope.split(" "));

            // Phân tách role và permission
            role = scopes.stream()
                    .filter(s -> s.startsWith("ROLE_"))
                    .findFirst()
                    .orElse("");

            permissions = scopes.stream().filter(s -> !s.startsWith("ROLE_")).collect(Collectors.toList());

            isValid = (username != null && !username.isEmpty());

        } catch (AppException | JOSEException | ParseException e) {
            return IntrospectResponse.builder().valid(false).user(null).build();
        }

        // Tìm người dùng trong DB
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        LoginResponse loginResponse = LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullname())
                .avatar(user.getProfileImg())
                .role(role)
                .permissions(permissions)
                .build();

        return IntrospectResponse.builder().valid(isValid).user(loginResponse).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Khai báo claims trong body
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("travel.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        // Ký token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cant create Token", e);
            throw new RuntimeException(e);
        }
    }

    // Tạo chuỗi các vai trò và quyền
    private String buildScope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        if (user.getRole() != null) {
            joiner.add("ROLE_" + user.getRole().getCode());
            List<RolePermission> rolePermissions = user.getRole().getRolePermissions();
            for (RolePermission rolePermission : rolePermissions) {
                joiner.add(rolePermission.getPermission().getCode());
            }
        }
        return joiner.toString();
    }

    // Xác thực token
    private SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if (!(verified && expirationTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Nếu token tồn tại trong bảng InvalidateToken
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    // Xác thưc token để cấp lại accesstoken
    private SignedJWT verifyRefreshToken(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESH_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli());

        boolean verified = signedJWT.verify(verifier);

        if (!(verified && expirationTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Nếu token tồn tại trong bảng InvalidateToken
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
