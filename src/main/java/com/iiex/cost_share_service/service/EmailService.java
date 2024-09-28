package com.iiex.cost_share_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${otp.expiration.minutes}")
    private int otpExpirationMinutes;

    public void send(String toEmail,String title, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(title);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

     public void sendOtpEmail(String toEmail, String otp ) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Your OTP Code");

            // Prepare the evaluation context
            Context context = new Context();
            context.setVariable("otp", otp);
            context.setVariable("expiryMinutes",otpExpirationMinutes );

            // Create the HTML body using Thymeleaf
            String htmlContent = templateEngine.process("otp-email.html", context);
            helper.setText(htmlContent, true); // true indicates HTML

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}
