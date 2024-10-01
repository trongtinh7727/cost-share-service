package com.iiex.cost_share_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.iiex.cost_share_service.entity.Otp;
import com.iiex.cost_share_service.utils.enums.VerifyType;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    List<Otp> findByEmailAndVerifyType(String email, VerifyType verifyType);
    List<Otp> findByOtpAndVerifyType(String otp, VerifyType verifyType);
    List<Otp> findByIsVerifiedAndEmailAndVerifyType(Boolean isverified, String email, VerifyType verifyType);

    void deleteById(Long id);
}
