package com.abhinavan.twilioLogin.dto;

import com.abhinavan.twilioLogin.enums.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseDto
{
    private String message;
    private OtpStatus otpStatus;
}
