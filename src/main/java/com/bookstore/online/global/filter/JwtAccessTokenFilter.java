package com.bookstore.online.global.filter;

import com.bookstore.online.domain.user.entity.UserEntity;
import com.bookstore.online.domain.user.service.token.ReadTokenService;
import com.bookstore.online.domain.user.service.user.ReadUserService;
import com.bookstore.online.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessTokenFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final ReadUserService readUserService;
  private final ReadTokenService readTokenService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String bearerToken = request.getHeader("Authorization");
    if (bearerToken == null || bearerToken.isBlank()) {
      filterChain.doFilter(request, response);
      return;
    }

    String accessToken = bearerToken.substring(7);

    // redis에 해당 토큰이 있는지 확인
    if (readTokenService.readCacheUser(accessToken) == null) {
      filterChain.doFilter(request, response);
      return;
    }

    Claims claims = null;

    try {
      claims = jwtProvider.getClaims(accessToken);
      String userId = (String) claims.get("userId");

      UserEntity user = readUserService.findUserByUserId(userId);

      if (user == null) {
        throw new JwtException("can't find user");
      }

      Authentication authentication =
          new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (Exception e) {
      e.printStackTrace();
      filterChain.doFilter(request, response);
      return;
    }

    filterChain.doFilter(request, response);
  }
}
