package com.paymentsystem.razorpay.payment.entity;


import com.paymentsystem.razorpay.common.entity.Money;
import com.paymentsystem.razorpay.common.enums.PaymentMethod;
import com.paymentsystem.razorpay.common.enums.PaymentStatus;
import com.paymentsystem.razorpay.merchant.entity.Merchant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_order_id",columnList = "order_id"),
        @Index(name = "idx_payment_merchant_id",columnList = "merchant_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "order_id",nullable = false)
    private OrderRecord order;

    @Column(nullable = false)
    private UUID merchant_id;

    @Embedded
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private PaymentMethod method;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "method_details",columnDefinition = "jsonb")
    private Map<String,Object> methodDetails;

    @Column(length = 100)
    private String bankReference;

    @Column(length = 100)
    private String processorReference;

    @Column(length = 255)
    private String errorDescription;

    private LocalDateTime authorizedAt;

    private LocalDateTime capturedAt;

    private LocalDateTime failedAt;

    private LocalDateTime refundedAt;

    private LocalDateTime settledAt;
}

























