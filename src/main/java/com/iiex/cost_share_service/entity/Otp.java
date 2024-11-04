package com.iiex.cost_share_service.entity;

import java.time.LocalDateTime;

import com.iiex.cost_share_service.utils.enums.VerifyType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Otps")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;
    @Column(nullable = false)
    private String otp;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "is_verified",nullable = false)
    private Boolean isVerified = false;
    @Column(name = "verifyType",nullable = false)
    @Enumerated(EnumType.STRING)
    private VerifyType verifyType;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
