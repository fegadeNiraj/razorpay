package com.paymentsystem.razorpay.operations.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "settlement_payment")
public class SettlementPayment {

    @EmbeddedId
    private SettlementPaymentId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("settlementId")
    @JoinColumn(name = "settlement_id",nullable = false)
    private Settlement settlement;

}
