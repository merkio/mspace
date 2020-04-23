package com.company.auth;

import com.company.auth.domain.User;
import com.company.auth.domain.UserRole;
import com.company.auth.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.util.Set;

@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AuthServiceApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(AuthServiceApplication.class, args);

        UserRepository userRepository = context.getBean(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userRepository.findAll().isEmpty()) {

            // init the users
            User user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .email("admin@company.com")
                .roles(Set.of(UserRole.ROLE_ADMIN.name()))
                .build();

            userRepository.save(user);
        }
    }

}
