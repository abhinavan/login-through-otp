package com.abhinavan.twilioLogin.services;

import com.abhinavan.twilioLogin.config.ApplicationConfiguration;
import com.abhinavan.twilioLogin.dto.PasswordResetRequestDto;
import com.abhinavan.twilioLogin.dto.PasswordResetResponseDto;
import com.abhinavan.twilioLogin.enums.OtpStatus;
import com.abhinavan.twilioLogin.exception.PassWordResetException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TwilioService
{

    private Map<String,String> otpMap = new HashMap<>();

    @Autowired
    private ApplicationConfiguration applicationConfiguration;


    public Mono<PasswordResetResponseDto> sendSmsForPasswordReset(PasswordResetRequestDto passwordResetRequestDto)
    {
        PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(applicationConfiguration.getTrial_number());
        String otp = generateOtp();
        String otpMessage = "Dear customer !! Your otp is "+otp;
        Message message = Message.creator(
                        to,
                        from,
                        otpMessage)
                .create();
        PasswordResetResponseDto passwordResetResponseDto = new PasswordResetResponseDto(otpMessage,OtpStatus.DELIVERED);
        otpMap.put(passwordResetRequestDto.getUsername(),otp);
        return Mono.just(passwordResetResponseDto);
    }

    @ExceptionHandler
    public Mono<PasswordResetResponseDto> handleException(Exception e)
    {
        return Mono.just(new PasswordResetResponseDto(e.getMessage(), OtpStatus.FAILED));
    }

    public Mono<String> validateOtp(String userInputOtp,String username) throws PassWordResetException
    {
        List<String> validationMessage = new ArrayList<>();
        otpMap.forEach((u,otp) -> {
            if(u.equals(username) && otp.equals(userInputOtp))
                validationMessage.add("OTP is valid");
            else
            {
                try {
                    throw new PassWordResetException("OTP is not valid");
                } catch (PassWordResetException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return Mono.just(validationMessage.get(0));
    }
    private String generateOtp()
    {
        return String.valueOf((int) (Math.random() * (1000000 - 100000) + 100000));
    }
}
