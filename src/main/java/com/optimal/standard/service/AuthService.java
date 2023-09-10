package com.optimal.standard.service;

import static com.optimal.standard.util.AuthUtils.collectAuthorities;

import com.optimal.standard.dto.LoginDTO;
import com.optimal.standard.dto.TokenInfo;
import com.optimal.standard.persistence.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;

  private AuthenticationManager authenticationManager;

  private JwtService jwtService;

  public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  public TokenInfo authenticate(LoginDTO request) {
    String username = request.getUsername();
    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
    var user = this.userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
    var jwtToken = this.jwtService.generateToken(user);
    this.jwtService.saveUserToken(user, jwtToken);
    return new TokenInfo(jwtToken, collectAuthorities(user.getAuthorities()));
  }

}
