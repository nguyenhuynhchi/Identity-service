package com.first_project.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final String[] PUBLIC_ENDPOINTS = {"/users", "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh"};

  @Autowired
  private CustomJwtDecoder customJwtDecoder;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(request ->
        request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
//            .requestMatchers(HttpMethod.GET, "/users")    //  PHÂN QUYỀN QUA ENDPOINT
//            .hasRole(Role.ADMIN.name())                   //  Với ROLE là ADMIN
            .anyRequest().authenticated());

    httpSecurity.oauth2ResourceServer(oauth2 ->
            oauth2.jwt(jwtConfigurer ->
                jwtConfigurer.decoder(customJwtDecoder)
                    .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
    );

    // httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
    // Rút ngắn bằng lambda cho câu lệnh trên
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    return httpSecurity.build();
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); // Gốc là có "ROLE_"

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }

  // Đã config ở class CustomJwtDecoder
//  @Bean
//  JwtDecoder jwtDecoder() {
//    SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),
//        "HS512");  // Thuật toán đã sử dụng trong generateToken
//    return NimbusJwtDecoder
//        .withSecretKey(secretKeySpec)
//        .macAlgorithm(MacAlgorithm.HS512)
//        .build();
//  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

}
