package com.travel.travel_booking_service.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.travel.travel_booking_service.dto.request.AuthenticationRequest;
import com.travel.travel_booking_service.dto.request.IntrospectRequest;
import com.travel.travel_booking_service.dto.request.LogoutRequest;
import com.travel.travel_booking_service.dto.request.RefreshRequest;
import com.travel.travel_booking_service.dto.response.AuthenticationResponse;
import com.travel.travel_booking_service.dto.response.IntrospectResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
