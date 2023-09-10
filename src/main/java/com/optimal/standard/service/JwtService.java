package com.optimal.standard.service;

import com.optimal.standard.persistence.model.RegisteredUser;
import com.optimal.standard.persistence.model.Token;
import com.optimal.standard.persistence.model.TokenType;
import com.optimal.standard.persistence.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class JwtService {

  public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long) 8; // 8 Horas

  private static final String JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEE=";

  private final TokenRepository tokenRepository;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .setSigningKey(JWT_SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();

    var rol = userDetails
        .getAuthorities()
        .stream()
        .toList()
        .get(0);
    claims.put("rol", rol);
    return createToken(claims, userDetails.getUsername());
  }

  public void saveUserToken(RegisteredUser user, String token) {
    this.tokenRepository.save(Token
        .builder()
        .user(user)
        .token(token)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build());
  }

  private String createToken(Map<String, Object> claims, String subject) {

    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
        .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
        .compact();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

}
