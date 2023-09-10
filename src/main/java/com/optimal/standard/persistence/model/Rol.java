package com.optimal.standard.persistence.model;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Rol {

  ADMIN,
  COMMENTOR,
  READONLY;

  public List<SimpleGrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
  }
}
