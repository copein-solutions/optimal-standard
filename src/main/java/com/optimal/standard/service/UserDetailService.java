package com.optimal.standard.service;

import com.optimal.standard.persistence.model.RegisteredUser;
import com.optimal.standard.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    RegisteredUser registeredUser = this.findUserByUsername(username);
    return User
        .withUsername(username)
        .password(registeredUser.getPassword())
        .roles(String.valueOf(registeredUser.getRol()))
        .build();
  }

  private RegisteredUser findUserByUsername(String username) {
    return this.userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

}
