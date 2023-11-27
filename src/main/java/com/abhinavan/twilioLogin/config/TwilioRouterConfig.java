package com.abhinavan.twilioLogin.config;

import com.abhinavan.twilioLogin.handler.TwilioOtpHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TwilioRouterConfig
{
    @Autowired
    private TwilioOtpHandler twilioOtpHandler;

    @Bean
    public RouterFunction<ServerResponse> handleSms()
    {
        return org.springframework.web.reactive.function.server.RouterFunctions.route()
                .POST("/sendOtp", twilioOtpHandler::sendOtp)
                .POST("/validateOtp", twilioOtpHandler::validateOtp)
                .build();
    }
}
