package com.iiex.cost_share_service.entities;

import java.time.LocalDateTime;

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
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
