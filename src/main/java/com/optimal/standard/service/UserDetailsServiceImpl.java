package com.optimal.standard.service;

import com.optimal.standard.persistence.model.RegisteredUser;
import com.optimal.standard.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    RegisteredUser registeredUser = this.userRepository.findByUsername(username);
    if (registeredUser == null) {
      throw new UsernameNotFoundException(username);
    }
    return User
        .withUsername(username)
        .password(registeredUser.getPassword())
        .roles(registeredUser.getRol())
        .build();
  }

}
