package com.optimal.standard.util;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public interface AuthUtils {

  static List<String> collectAuthorities(Collection<? extends GrantedAuthority> grantedAuthority) {
    return grantedAuthority
        .stream()
        .map(GrantedAuthority::getAuthority)
        .toList();
  }

}
