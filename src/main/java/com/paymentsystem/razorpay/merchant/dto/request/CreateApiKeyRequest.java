package com.paymentsystem.razorpay.merchant.dto.request;

import com.paymentsystem.razorpay.common.enums.Environment;

public record CreateApiKeyRequest(
        Environment environment
) {
}
