package com.iiex.cost_share_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.dto.CreateUserRequest;
import com.iiex.cost_share_service.dto.CreateUserResponse;
import com.iiex.cost_share_service.dto.LoginRequest;
import com.iiex.cost_share_service.dto.SendVerifyCodeRequest;
import com.iiex.cost_share_service.dto.SendVerifyCodeResponse;
import com.iiex.cost_share_service.dto.VerifyOTPRequest;
import com.iiex.cost_share_service.dto.VerifyOTPResponse;
import com.iiex.cost_share_service.entity.Otp;
import com.iiex.cost_share_service.entity.User;
import com.iiex.cost_share_service.exception.OtpValidationException;
import com.iiex.cost_share_service.repository.OtpRepository;
import com.iiex.cost_share_service.repository.UserRepository;
import com.iiex.cost_share_service.security.jwt.JwtUtils;
import com.iiex.cost_share_service.security.user.CustomUserDetails;
import com.iiex.cost_share_service.utils.enums.VerifyType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authorizationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;

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
        List<Otp> existingOtps = otpRepository.findByEmailAndVerifyType(request.getEmail(), request.getVerifyType());
        existingOtps.forEach(otp -> otpRepository.deleteById(otp.getOtpId()));

        // Create new OTP
        Otp otpEntity = new Otp();
        String otp = generateOtp();
        otpEntity.setOtp(otp);
        otpEntity.setEmail(request.getEmail());
        otpEntity.setExpiryDate(LocalDateTime.now().plusMinutes(otpExpiration));
        otpEntity.setVerifyType(request.getVerifyType());
        otpRepository.save(otpEntity);
        emailService.sendOtpEmail(request.getEmail(), otp);
        return new SendVerifyCodeResponse("Sent OTP successfully. Please check your email for OTP.");
    }

    public VerifyOTPResponse validateOtp(VerifyOTPRequest verifyOTPRequest) {
        List<Otp> otpList = otpRepository.findByEmailAndVerifyType(verifyOTPRequest.getEmail(),
                verifyOTPRequest.getVerifyType());
        if (otpList.isEmpty()) {
            throw new OtpValidationException("OTP not found for the provided email.");
        } else {
            Otp otpEntity = otpList.get(0);
            if (otpEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
                otpRepository.delete(otpEntity);
                throw new OtpValidationException("OTP has expired.");
            }
            if (!otpEntity.getOtp().equals(verifyOTPRequest.getOtp())) {
                throw new OtpValidationException("Invalid OTP.");
            }
            // OTP is valid; set verified to true
            otpEntity.setIsVerified(true);
            otpRepository.save(otpEntity);
            return new VerifyOTPResponse("Successfully");
        }
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        List<Otp> otpList = otpRepository.findByIsVerifiedAndEmailAndVerifyType(true, request.getEmail(),
                VerifyType.Register);
        if (otpList.isEmpty()) {
            throw new OtpValidationException("OTP not validate for the provided email.");
        } else {
            Otp otp = otpList.get(0);
            if (otp.getExpiryDate().isBefore(LocalDateTime.now())) {
                otpRepository.delete(otp);
                throw new OtpValidationException("OTP has expired.");
            }
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setUsername(request.getUsername());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            Authentication authentication = authorizationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String token = jwtUtils.generateTokenForUser(authentication);
            otpRepository.delete(otp);
            return new CreateUserResponse(token);
        }
    }

    public CreateUserResponse login(LoginRequest request) {

        Authentication authentication = authorizationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtUtils.generateTokenForUser(authentication);
        CustomUserDetails userDetail =  (CustomUserDetails) authentication.getPrincipal();
        return new CreateUserResponse(token);
    }

}
