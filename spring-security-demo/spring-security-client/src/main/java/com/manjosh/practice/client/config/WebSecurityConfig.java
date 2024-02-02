package com.manjosh.practice.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
            "/hello",
            "register",
            "verifyRegistration*",
            "resendVerificationToken*",
            "savePassword",
            "resetPassword",
            "changePassword"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> r.requestMatchers(WHITE_LIST_URLS).permitAll())
                .authorizeHttpRequests(r -> r.requestMatchers("/api/**").authenticated())
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/api-client-oidc"))
                .oauth2Client(Customizer.withDefaults())
                .build();
    }
}
