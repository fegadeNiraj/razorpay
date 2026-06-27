package com.paymentsystem.razorpay.merchant.service;

import com.paymentsystem.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.paymentsystem.razorpay.merchant.dto.response.MerchantResponse;

public interface AuthService {
    MerchantResponse signUp(MerchantSignupRequest request);
}
