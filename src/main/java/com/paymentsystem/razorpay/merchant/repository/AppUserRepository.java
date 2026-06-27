package com.paymentsystem.razorpay.merchant.repository;

import com.paymentsystem.razorpay.merchant.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
}
