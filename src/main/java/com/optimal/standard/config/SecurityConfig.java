package com.optimal.standard.config;

import com.optimal.standard.persistence.repository.UserRepository;
import com.optimal.standard.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserRepository userRepository;

  public SecurityConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  SecurityFilterChain web(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable() // (2)
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/public/**")
            .permitAll()
            .requestMatchers("/api/**")
            .hasRole("ADMIN")
            .anyRequest()
            .authenticated());
    return http.build();
  }

  @Bean
  UserDetailsServiceImpl userDetailsService() {
    return new UserDetailsServiceImpl(this.userRepository);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
