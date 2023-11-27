package com.abhinavan.twilioLogin.handler;

import com.abhinavan.twilioLogin.dto.PasswordResetRequestDto;
import com.abhinavan.twilioLogin.exception.PassWordResetException;
import com.abhinavan.twilioLogin.services.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TwilioOtpHandler
{

    @Autowired
    private TwilioService twilioService;
    public Mono<ServerResponse> sendOtp(ServerRequest serverRequest)
    {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(passwordResetRequestDto ->
                        twilioService.sendSmsForPasswordReset(passwordResetRequestDto))
                .flatMap(passwordResetResponseDto ->
                        ServerResponse.ok().body(BodyInserters.fromValue(passwordResetResponseDto)));
    }

    public Mono<ServerResponse> validateOtp(ServerRequest serverRequest)
    {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(passwordResetRequestDto ->
                {
                    try {
                        return twilioService.validateOtp(passwordResetRequestDto.getOneTimePassword(),passwordResetRequestDto.getUsername());
                    } catch (PassWordResetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(validationMessage ->
                        ServerResponse.ok().body(BodyInserters.fromValue(validationMessage)));
    }
}
