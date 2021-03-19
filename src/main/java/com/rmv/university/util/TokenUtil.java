package com.rmv.university.util;

import com.rmv.university.entity.dao.User;
import com.rmv.university.repo.UserRepo;
import com.rmv.university.service.AuthorizationService;

import java.util.Optional;

public final class TokenUtil {

  private static UserRepo userRepo = UserRepo.INSTANCE;
  public static User getUserByToken(String token) {
    Integer userId = AuthorizationService.getUserId(token);
    Optional<User> optionalUser = userRepo.findById(userId);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else throw new RuntimeException("e");
  }

  private TokenUtil() {}
}
