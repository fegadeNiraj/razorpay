package com.paymentsystem.razorpay.merchant.repository;

import com.paymentsystem.razorpay.merchant.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {

    List<ApiKey> findByMerchantId(UUID merchantId);

}
