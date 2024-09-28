package com.iiex.cost_share_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entities.Otp;

import java.util.List;


public interface OtpRepository extends JpaRepository<Otp,Long> {
    List<Otp> findByEmail(String email);
    void deleteById(Long id);
}
