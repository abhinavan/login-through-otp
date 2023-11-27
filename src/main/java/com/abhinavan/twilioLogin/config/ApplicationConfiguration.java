package com.abhinavan.twilioLogin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class ApplicationConfiguration
{
    private String account_sid;
    private String auth_token;
    private String trial_number;
}
