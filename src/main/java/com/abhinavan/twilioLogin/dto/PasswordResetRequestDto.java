package com.abhinavan.twilioLogin.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto
{
    private String username;
    private String oneTimePassword;
    private String phoneNumber;

}
