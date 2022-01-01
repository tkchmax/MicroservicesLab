package com.chessplatform.serviceuser;

import com.chessplatform.serviceuser.repo.model.Role;
import com.chessplatform.serviceuser.repo.model.User;
import com.chessplatform.serviceuser.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }


     @Bean
     CommandLineRunner run(UserService userService)
     {
      return args-> {
       userService.saveRole(new Role(null, "ROLE_GUEST"));
       userService.saveRole(new Role( null,"ROLE_PLAYER"));
       userService.saveRole(new Role( null,"ROLE_ADMIN"));

       userService.createAdmin();
     };
     }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
