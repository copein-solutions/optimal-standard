package com.optimal.standard.filters;

import com.optimal.standard.persistence.repository.TokenRepository;
import com.optimal.standard.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final TokenRepository tokenRepository;

  private UserDetailsService userDetailsService;

  private JwtService jwtService;

  public JwtRequestFilter(TokenRepository tokenRepository, UserDetailsService userDetailsService, JwtService jwtService) {
    this.tokenRepository = tokenRepository;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = this.jwtService.extractUsername(jwt);
    }

    if (username != null && SecurityContextHolder
        .getContext()
        .getAuthentication() == null) {

      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      var isTokenValid = this.tokenRepository
          .findByToken(jwt)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);

      if (this.jwtService.validateToken(jwt, userDetails) && isTokenValid) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder
            .getContext()
            .setAuthentication(authenticationToken);
      }
    }
    chain.doFilter(request, response);
  }

}
