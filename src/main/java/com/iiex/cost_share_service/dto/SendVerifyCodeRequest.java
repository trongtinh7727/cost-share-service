package com.iiex.cost_share_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendVerifyCodeRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;
}
