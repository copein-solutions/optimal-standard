package com.optimal.standard.config;

import static com.optimal.standard.persistence.model.Rol.ADMIN;
import static com.optimal.standard.persistence.model.Rol.COMMENTOR;
import static com.optimal.standard.persistence.model.Rol.READONLY;
import static org.springframework.security.config.Customizer.withDefaults;

import com.optimal.standard.filters.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private static final String LOGOUT_URL = "/optimal_standard/user/logout";

  private static final String PUBLIC_MATCHER = "/*/public/**";

  private static final String ADMIN_MATCHER = "/*/admin/**";

  private static final String USER_MATCHER = "/*/user/**";

  private final LogoutHandler logoutHandler;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Bean
  SecurityFilterChain web(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeRequests(authorize -> authorize
            .requestMatchers(PUBLIC_MATCHER)
            .permitAll()
            .requestMatchers(ADMIN_MATCHER)
            .hasRole("ADMIN")
            .requestMatchers(USER_MATCHER)
            .hasAnyRole(ADMIN.name(), COMMENTOR.name(), READONLY.name())
            .requestMatchers(HttpMethod.GET, USER_MATCHER)
            .hasRole("READONLY")
            .anyRequest()
            .authenticated())
        .cors(withDefaults())
        .addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .logout()
        .logoutUrl(LOGOUT_URL)
        .addLogoutHandler(this.logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
    return http.build();
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
