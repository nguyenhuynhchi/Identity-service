package com.first_project.test.service;

import com.first_project.test.dto.request.AuthenticationRequest;
import com.first_project.test.dto.request.IntrospectRequest;
import com.first_project.test.dto.response.AuthenticationResponse;
import com.first_project.test.dto.response.IntrospectResponse;
import com.first_project.test.entity.User;
import com.first_project.test.exception.AppException;
import com.first_project.test.exception.ErrorCode;
import com.first_project.test.repository.UserRepository;

//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.JWSObject;
//import com.nimbusds.jose.JWSVerifier;
//import com.nimbusds.jose.Payload;
import com.nimbusds.jose.*;

import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {

  UserRepository userRepository;

  @NonFinal
  @Value("${jwt.signerKey}")
  protected String SIGNER_KEY;


  public IntrospectResponse introspect(IntrospectRequest request)
      throws ParseException, JOSEException {
    var token = request.getToken();
    JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

    SignedJWT signedJWT = SignedJWT.parse(token);

    Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

    var verified = signedJWT.verify(verifier);

    return IntrospectResponse.builder()
        .valid(verified && expiryTime.after(new Date()))
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    var user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

    if (!authenticated) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    var token = generateToken(user);

    return AuthenticationResponse.builder()
        .token(token)
        .authenticated(true)
        .build();
  }

  private String generateToken(User user) {
    // Tạo JWS Header với thuật toán HS512 dủ mạnh để bảo vệ token
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    // Tạo JWT Claims Set
    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(user.getUsername())
        .issuer("huynhchi.com") // Tên miền
        .issueTime(new Date())
        .expirationTime(new Date(
            Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
        )) // Token hết hạn sau 1 giờ
        .claim("scope", buildScope(user))
        .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(header, payload);
    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }

  private String buildScope(User user){
    StringJoiner stringJoiner = new StringJoiner("");

    // GỐC
    if(!CollectionUtils.isEmpty(user.getRoles()))
      user.getRoles().forEach(role -> {
        stringJoiner.add("ROLE_"+role.getName());
        if (!CollectionUtils.isEmpty(role.getPermissions()))
          role.getPermissions()
              .forEach(permission ->
                  stringJoiner.add(permission.getName()));
          });

    return stringJoiner.toString();
  }
}
