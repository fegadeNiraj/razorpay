package com.paymentsystem.razorpay.merchant.service.impl;

import com.paymentsystem.razorpay.common.exception.ResourceNotFoundException;
import com.paymentsystem.razorpay.common.util.RandomizerUtil;
import com.paymentsystem.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.paymentsystem.razorpay.merchant.dto.response.ApiKeyResponse;
import com.paymentsystem.razorpay.merchant.dto.response.CreateApiKeyResponse;
import com.paymentsystem.razorpay.merchant.entity.ApiKey;
import com.paymentsystem.razorpay.merchant.entity.Merchant;
import com.paymentsystem.razorpay.merchant.mapper.ApiKeyMapper;
import com.paymentsystem.razorpay.merchant.repository.ApiKeyRepository;
import com.paymentsystem.razorpay.merchant.repository.MerchantRepository;
import com.paymentsystem.razorpay.merchant.service.ApiKeyService;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyMapper apiKeyMapper;
    private final MerchantRepository merchantRepository;

    @Override
    @Transactional
    public CreateApiKeyResponse create(UUID merchantId, CreateApiKeyRequest request) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(
                () -> new ResourceNotFoundException("merchant", merchantId)
        );

        String keyId = "rzp_"+request.environment().name().toLowerCase()+"_"+ RandomizerUtil.randomBase64(24);
        String rawSecret = RandomizerUtil.randomBase64(40);

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret)
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new CreateApiKeyResponse(apiKey.getId(), keyId , rawSecret , request.environment());

    }

    @Override
    @Transactional
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {
        return apiKeyMapper.toResponseList(apiKeyRepository.findByMerchantId(merchantId));
    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()->new ResourceNotFoundException("ApiKey",keyId));

        apiKey.setEnabled(false);
    }

    @Nullable
    @Override
    @Transactional
    public CreateApiKeyResponse rotate(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()->new ResourceNotFoundException("ApiKey",keyId));

        if(!apiKey.isEnabled()) throw new RuntimeException("Cannot rotate a disabled key");

        String newRawSecret = RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret);
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24));
        apiKey = apiKeyRepository.save(apiKey);

        return new CreateApiKeyResponse(apiKey.getId(), apiKey.getKeyId(),
                newRawSecret, apiKey.getEnvironment());
    }
}
