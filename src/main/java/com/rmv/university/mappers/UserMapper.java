package com.rmv.university.mappers;

import com.rmv.university.entity.User;
import com.rmv.university.entity.response.UserResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
  public static UserMapper INSTANCE = new UserMapper();

  private UserMapper() {}

  public User resultSetToEntity(ResultSet resultSet) throws SQLException {
    User user = new User();
    user.setId(resultSet.getInt("id"));
    user.setUsername(resultSet.getString("username"));
    user.setPassword(resultSet.getString("password"));
    user.setStudentId(resultSet.getInt("student_id"));
    user.setLecturerId(resultSet.getInt("lecturer_id"));
    return user;
  }

  public UserResponse toUserResponse(User user) {
    UserResponse response = new UserResponse();
    response.setId(user.getId());
    response.setUsername(user.getUsername());
    response.setStudentId(user.getStudentId());
    response.setLecturerId(user.getLecturerId());
    return response;
  }
}
