package com.first_project.test.controller;

import com.first_project.test.dto.request.ApiResponse;
import com.first_project.test.dto.request.AuthenticationRequest;
import com.first_project.test.dto.request.IntrospectRequest;
import com.first_project.test.dto.request.LogoutRequest;
import com.first_project.test.dto.request.RefreshRequest;
import com.first_project.test.dto.response.AuthenticationResponse;
import com.first_project.test.dto.response.IntrospectResponse;
import com.first_project.test.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
  AuthenticationService authenticationService;

  // log-in và generate token
  @PostMapping("/token")
  ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    var result = authenticationService.authenticate(request);

    return ApiResponse.<AuthenticationResponse>builder()
        .result(result).build();
  }

  // Kiểm tra token
  @PostMapping("/introspect")
  ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
      throws ParseException, JOSEException {
    var result = authenticationService.introspect(request);
    return ApiResponse.<IntrospectResponse>builder()
        .result(result)
        .build();
  }

  // log-in và generate token
  @PostMapping("/refresh")
  ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
      throws ParseException, JOSEException {
    var result = authenticationService.refreshToken(request);

    return ApiResponse.<AuthenticationResponse>builder()
        .result(result).build();
  }

  @PostMapping("/logout")
  ApiResponse<Void> logout(@RequestBody LogoutRequest request)
      throws ParseException, JOSEException {
    authenticationService.logout(request);
    return ApiResponse.<Void>builder().build();
  }
}
