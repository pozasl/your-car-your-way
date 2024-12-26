package com.ycyw.graphql.service;

import java.util.stream.Collectors;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.ycyw.graphql.entity.UserDetailEntity;

/**
 * JWT service implementation.
 */
@Service
public class JwtServiceImpl implements JwtService {

  private final JwtEncoder encoder;

  @Autowired
  public JwtServiceImpl(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  @Override
  public String generateToken(Authentication auth) {
    UserDetailEntity userDetails = (UserDetailEntity) auth.getPrincipal();
    String scope = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    return createToken(userDetails, scope, this.encoder);
  }

  public String generateTokenFromUserdetail(UserDetailEntity userDetails) {
    String scope = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    return createToken(userDetails, scope , this.encoder);
  }

  /**
   * Static method to create a JWT Token.
   *
   * @param userDetails the user details
   * @param scope the user's authorities scope
   * @param encoder the password encoder
   * @return a JWT token
   */
  public static String createToken(
    UserDetailEntity userDetails,
      String scope,
      JwtEncoder encoder
  ) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("MDD")
        .issuedAt(now)
        .expiresAt(now.plus(24, ChronoUnit.HOURS))
        .subject(userDetails.getEmail())
        .claim("scope", scope)
        .claim("userId", userDetails.getId())
        .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
  
}