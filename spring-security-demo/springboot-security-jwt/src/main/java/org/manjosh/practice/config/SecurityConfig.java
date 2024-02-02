package org.manjosh.practice.config;

import lombok.RequiredArgsConstructor;
import org.manjosh.practice.entity.UserInfo;
import org.manjosh.practice.filters.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter authFilter;
    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    //authentication
    @Bean
    public UserDetailsService userDetailsService() {
        //hard coding the user details
        /*UserDetails admin = User.withUsername("manjosh")
                .password(encoder.encode("manjosh")).roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("guest")
                .password(encoder.encode("guest")).roles("GUEST")
                .build();
        return new InMemoryUserDetailsManager(admin, user);*/
        return new CustomUserDetailsService();
    }

    //authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/products/welcome","/auth/register","/auth/refresh","/auth/authenticate","/h2-console/*").permitAll()
                        .requestMatchers("/products/**").authenticated()
                )
                .exceptionHandling(exception ->exception.authenticationEntryPoint(userAuthenticationEntryPoint))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionMgmnt -> sessionMgmnt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
