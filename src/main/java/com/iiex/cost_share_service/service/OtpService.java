package com.iiex.cost_share_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.dto.SendVerifyCodeRequest;
import com.iiex.cost_share_service.dto.SendVerifyCodeResponse;
import com.iiex.cost_share_service.entities.Otp;
import com.iiex.cost_share_service.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Value;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private EmailService emailService;

    @Value("${otp.expiration.minutes}")
    private int otpExpiration;

    public String generateOtp() {
        // Generate a 6-digit random OTP
        Random random = new Random();
        int otpInt = 100000 + random.nextInt(900000);
        return String.valueOf(otpInt);
    }

    public SendVerifyCodeResponse createOtp(SendVerifyCodeRequest request) {
        // Remove existing OTP if any
        List<Otp> existingOtps = otpRepository.findByEmail(request.getEmail());
        existingOtps.forEach(otp -> otpRepository.deleteById(otp.getOtpId()));

        // Create new OTP
        Otp otpEntity = new Otp();
        String otp = generateOtp();
        otpEntity.setOtp(otp);
        otpEntity.setEmail(request.getEmail());
        otpEntity.setExpiryDate(LocalDateTime.now().plusMinutes(otpExpiration));
        otpRepository.save(otpEntity);
        emailService.sendOtpEmail(request.getEmail(),otp);
        return new SendVerifyCodeResponse("Sent OTP successfully. Please check your email for OTP.");
    }

    public boolean validateOtp(String email, String otp) {
        List<Otp> otpList = otpRepository.findByEmail(email);
        if (otpList.isEmpty()) {
            
        }else{
            Otp otpEntity = otpList.get(0);
            if (otpEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
                
            }
    
            if (!otpEntity.getOtp().equals(otp)) {
                
            }
    
            // OTP is valid; delete it
            otpRepository.delete(otpEntity);
        }
        

        
        return true;
    }

}
