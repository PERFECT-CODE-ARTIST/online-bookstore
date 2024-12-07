package com.bookstore.online.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

  private final Key key;

  public JwtProvider(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }

  public String generateAccessToken(String userId) {
    // 현재로부터 하루
    Date expireDate = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24));
    return Jwts.builder()
        .claim("userId", userId)
        .expiration(expireDate)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims getClaims(String token) {
    JwtParser jwtParser = Jwts.parser().setSigningKey(key).build();
    return jwtParser.parseClaimsJws(token).getPayload();
  }
}