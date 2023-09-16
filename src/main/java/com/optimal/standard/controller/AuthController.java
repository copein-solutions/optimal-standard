package com.optimal.standard.controller;

import com.optimal.standard.dto.LoginDTO;
import com.optimal.standard.dto.TokenInfo;
import com.optimal.standard.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/optimal_standard")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/public/login")
  public ResponseEntity<TokenInfo> login(@RequestBody LoginDTO request) {
    return ResponseEntity.ok(this.authService.authenticate(request));
  }

}
