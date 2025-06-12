package com.travel.travel_booking_service.service;

import java.io.UnsupportedEncodingException;

import jakarta.servlet.http.HttpServletRequest;

import com.travel.travel_booking_service.dto.request.PaymentRequest;

public interface VNPAYService {
    String createPaymentUrl(PaymentRequest paymentRequest, HttpServletRequest httpServletRequest)
            throws UnsupportedEncodingException;
}
