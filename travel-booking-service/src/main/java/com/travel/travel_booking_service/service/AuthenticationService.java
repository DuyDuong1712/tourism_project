package com.travel.travel_booking_service.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.travel.travel_booking_service.dto.request.*;
import com.travel.travel_booking_service.dto.response.AuthenticationResponse;
import com.travel.travel_booking_service.dto.response.IntrospectResponse;
import com.travel.travel_booking_service.dto.response.UserResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    UserResponse registerUser(UserCreationRequest request);
}
