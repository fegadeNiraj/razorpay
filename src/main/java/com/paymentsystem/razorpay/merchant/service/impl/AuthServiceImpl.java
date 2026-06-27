package com.paymentsystem.razorpay.merchant.service.impl;

import com.paymentsystem.razorpay.common.enums.MerchantStatus;
import com.paymentsystem.razorpay.common.enums.UserRole;
import com.paymentsystem.razorpay.common.exception.DuplicateResourceException;
import com.paymentsystem.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.paymentsystem.razorpay.merchant.dto.response.MerchantResponse;
import com.paymentsystem.razorpay.merchant.entity.AppUser;
import com.paymentsystem.razorpay.merchant.entity.Merchant;
import com.paymentsystem.razorpay.merchant.mapper.MerchantMapper;
import com.paymentsystem.razorpay.merchant.repository.AppUserRepository;
import com.paymentsystem.razorpay.merchant.repository.MerchantRepository;
import com.paymentsystem.razorpay.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public MerchantResponse signUp(MerchantSignupRequest request) {
        if(merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL",
                    "Merchant with email already exists: " +request.email());
        }

        Merchant merchant = merchantMapper.toEntityFromSignUpRequest(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);

        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password()) //TODO: encrypt using Bcrypt
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);
        return merchantMapper.toResponse(merchant);
    }
}
