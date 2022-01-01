package com.chessplatform.serviceclaims;

import com.chessplatform.serviceclaims.service.ClaimsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServiceClaimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceClaimsApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ClaimsService claimsService)
    {
        return args-> {
            claimsService.addReport("cheating", "user1", "user2");
        };
    }
}
