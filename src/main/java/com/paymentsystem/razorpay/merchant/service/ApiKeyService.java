package com.paymentsystem.razorpay.merchant.service;

import com.paymentsystem.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.paymentsystem.razorpay.merchant.dto.response.ApiKeyResponse;
import com.paymentsystem.razorpay.merchant.dto.response.CreateApiKeyResponse;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {

    CreateApiKeyResponse create(UUID merchantId, CreateApiKeyRequest request);

    List<ApiKeyResponse> listByMerchant(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);

    @Nullable CreateApiKeyResponse rotate(UUID merchantId, UUID keyId);
}
