package com.iiex.cost_share_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.dto.request.*;
import com.iiex.cost_share_service.dto.response.ApiResponse;
import com.iiex.cost_share_service.dto.response.CreateUserResponse;
import com.iiex.cost_share_service.entity.Otp;
import com.iiex.cost_share_service.entity.User;
import com.iiex.cost_share_service.exception.OtpValidationException;
import com.iiex.cost_share_service.repository.OtpRepository;
import com.iiex.cost_share_service.repository.UserRepository;
import com.iiex.cost_share_service.security.jwt.JwtUtils;
import com.iiex.cost_share_service.utils.enums.AppRole;
import com.iiex.cost_share_service.utils.enums.VerifyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

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

    public ApiResponse<?> createOtp(SendVerifyCodeRequest request) {
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
        return new ApiResponse<>("Sent OTP successfully. Please check your email for OTP.");
    }

    public ApiResponse<?> validateOtp(VerifyOTPRequest verifyOTPRequest) {
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
            return new ApiResponse<>("Successfully");
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
            user.setRole(AppRole.USER.name());
            userRepository.save(user);
            otpRepository.delete(otp);
            Authentication authentication = authorizationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            String token = jwtUtils.generateTokenForUser(authentication);
            return new CreateUserResponse(token);
        }
    }

    public CreateUserResponse login(LoginRequest request) {
        Authentication authentication = authorizationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtUtils.generateTokenForUser(authentication);
        return new CreateUserResponse(token);
    }

    public CreateUserResponse login(OAuth2AuthenticationToken authentication) {
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        String email = (String) attributes.get("email");
        String userId = (String) attributes.get("sub");
        String name = (String) attributes.get("name");
        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(AppRole.USER.name());
        String token = jwtUtils.generateTokenForOAuthUser(email, userId);
        return new CreateUserResponse(token);
    }
}
