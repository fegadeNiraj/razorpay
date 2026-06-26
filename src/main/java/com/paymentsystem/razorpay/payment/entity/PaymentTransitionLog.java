package com.paymentsystem.razorpay.payment.entity;


import com.paymentsystem.razorpay.common.enums.PaymentActor;
import com.paymentsystem.razorpay.common.enums.PaymentEvent;
import com.paymentsystem.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_transition_log", indexes = {
        @Index(name="idx_payment_transition_log_payment_id", columnList = "payment_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTransitionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status",nullable = false, length = 30)
    private PaymentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status",nullable = false, length = 30)
    private PaymentStatus toStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "event", nullable = false, length = 30)
    private PaymentEvent paymentEvent;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor", nullable = false, length = 100)
    private PaymentActor paymentActor;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;
}
