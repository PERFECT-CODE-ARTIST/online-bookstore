package com.bookstore.online.global.config.security;

import com.bookstore.online.global.filter.JwtAccessTokenFilter;
import com.bookstore.online.global.handler.AuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAccessTokenFilter jwtAccessTokenFilter;
  private final AuthenticationHandler authenticationHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic(HttpBasicConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(CsrfConfigurer::disable)
        .cors(cors -> cors.configurationSource(configurationSource()))
        .authorizeHttpRequests(request -> request
            .requestMatchers("/swagger","/swagger-ui.html","/swagger-ui/**","api-docs/**","/v3/api-docs/**","/","/api/v1/user/**").permitAll()
            .requestMatchers(HttpMethod.GET,
                "/api/v1/book/**",
                "/api/v1/category/**",
                "/api/v1/orders/**",
                "/api/v1/order-items",
                "/api/v1/review/**").permitAll().anyRequest().authenticated())
        .addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationHandler));
    return http.build();
  }

  @Bean
  protected CorsConfigurationSource configurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}