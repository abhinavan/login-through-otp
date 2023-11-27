package com.abhinavan.twilioLogin;

import com.abhinavan.twilioLogin.config.ApplicationConfiguration;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginThroughOtpApplication {

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@PostConstruct
	public void initTwilio()
	{
		Twilio.init(applicationConfiguration.getAccount_sid(), applicationConfiguration.getAuth_token());
	}

	public static void main(String[] args) {
		SpringApplication.run(LoginThroughOtpApplication.class, args);
	}

}
