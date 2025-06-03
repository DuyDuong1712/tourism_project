package com.travel.travel_booking_service.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {
        "/users", "/auth/login", "/auth/introspect", "/auth/logout", "/auth/refresh"
    };

    private final String FE_ADMIN = "http://localhost:4000";
    private final String FE_CLIENT = "http://localhost:3000";

    @Autowired
    private CustomerJWTDecoder customerJWTDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(
                AbstractHttpConfigurer
                        ::disable); // Tat CSRF. JWT đã cung cấp cơ chế xác thực riêng và không phụ thuộc vào session
        // hoặc cookie (nơi CSRF thường được áp dụng).

        httpSecurity.authorizeHttpRequests(
                requests -> requests.anyRequest().permitAll() // Tất cả request phải đc authenticated mới đc access
                );

        // xác thực các JWT được gửi trong request
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer ->
                jwtConfigurer.decoder(customerJWTDecoder).jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return httpSecurity.build();
        // spotless:on
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        //        config.addAllowedOrigin(FE_CLIENT);
        //        config.addAllowedOrigin(FE_ADMIN);
        //        config.addAllowedMethod("*");
        //        config.addAllowedHeader("*");
        config.setAllowedOrigins(Arrays.asList(FE_CLIENT, FE_ADMIN));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        // ánh xạ claim trong JWT thành quyền (authority).
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); đã cấu hình thêm ROLE_ trong
        // AuthenticationService
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
