package com.paymentsystem.razorpay.merchant.mapper;

import com.paymentsystem.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.paymentsystem.razorpay.merchant.dto.response.MerchantResponse;
import com.paymentsystem.razorpay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {

    Merchant toEntityFromSignUpRequest(MerchantSignupRequest request);

    @Mapping(source = "status", target = "merchantStatus")
    MerchantResponse toResponse(Merchant merchant);

}
