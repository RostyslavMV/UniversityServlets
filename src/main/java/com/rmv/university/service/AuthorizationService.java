package com.rmv.university.service;

import com.rmv.university.entity.dao.User;
import com.rmv.university.util.ApplicationProperties;
import com.rmv.university.util.AuthRole;
import com.rmv.university.util.Encryptor;
import io.jsonwebtoken.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationService {
  public static AuthorizationService INSTANCE = new AuthorizationService();

  private static final UserService userService = UserService.INSTANCE;

  private static final String signature = ApplicationProperties.get("signature");
  private static final Long ttlMillis = Long.valueOf(ApplicationProperties.get("ttlMillis"));

  private AuthorizationService() {}

  public String authorize(String username, String password) {
    User user = userService.getByUsername(username);

    if (!user.getPassword().equals(Encryptor.encode(password))) {
      throw new RuntimeException("Wrong credentials");
    }

    return createJWT(user);
  }

  public static String createJWT(User user) {
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    String authorities = "STUDENT";
    if (!user.isStudent()) {
      authorities = "LECTURER";
    }

    JwtBuilder builder =
        Jwts.builder()
            .setIssuedAt(now)
            .setSubject(user.getUsername())
            .claim("authorities", authorities)
            .claim("user_id", user.getId())
            .setExpiration(new Date(nowMillis + ttlMillis))
            .signWith(SignatureAlgorithm.HS256, signature);

    return builder.compact();
  }

  public static Jws<Claims> parseToken(String token) {
    return Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
  }

  public static AuthRole getRole(String token) {
    Jws<Claims> claimsJws = parseToken(token);
    String authoritiesStr = claimsJws.getBody().get("authorities").toString();
    return AuthRole.valueOf(authoritiesStr);
  }

  public static Long getUserId(String token) {
    Jws<Claims> claimsJws = parseToken(token);
    return Long.parseLong(claimsJws.getBody().get("user_id").toString());
  }
}
