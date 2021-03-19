package com.rmv.university.service;

import com.rmv.university.db.ConnectionFactory;
import com.rmv.university.entity.dao.User;
import com.rmv.university.entity.request.UserCreateRequest;
import com.rmv.university.repo.LecturerRepo;
import com.rmv.university.repo.StudentRepo;
import com.rmv.university.repo.UserRepo;
import com.rmv.university.util.Encryptor;

import java.sql.Connection;
import java.util.Optional;

public class UserService {
  public static UserService INSTANCE = new UserService();

  private UserRepo userRepo = UserRepo.INSTANCE;
  private final LecturerRepo lecturerRepo = LecturerRepo.INSTANCE;
  private final StudentRepo studentRepo = StudentRepo.INSTANCE;

  private UserService() {}

  //    public User getById(Long id) {
  //        return userRepo.findById(id)
  //                .orElseThrow(() -> new RuntimeException(String.format("Can't find user by
  // id=%d", id)));
  //    }

  public Optional<User> findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  public User getByUsername(String username) {
    return findByUsername(username)
        .orElseThrow(
            () -> new RuntimeException(String.format("Can't find user by username=%s", username)));
  }

  public User create(UserCreateRequest request) {

    if (findByUsername(request.getUsername()).isPresent()) {
      throw new RuntimeException("User with username already exists");
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(Encryptor.encode(request.getPassword()));
    user.setFirstName(request.getFirstName());
    user.setSurname(request.getSurname());
    user.setPatronymic(request.getPatronymic());
    if (request.getRole().equals("student")) {
      user.setStudent(true);
    }

    Connection connection = ConnectionFactory.getConnection();

    try {
      ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
      if (user.isStudent()) {
        studentRepo.save(user);
      } else {
        lecturerRepo.save(user);
      }
      user = userRepo.save(user);
      ConnectionFactory.commitTransaction(connection);
      return user;
    } catch (Exception e) {
      ConnectionFactory.rollbackTransaction(connection);
      throw new RuntimeException(e);
    } finally {
      ConnectionFactory.close(connection);
    }
  }
}
