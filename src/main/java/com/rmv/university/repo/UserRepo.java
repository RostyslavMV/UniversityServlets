package com.rmv.university.repo;

import com.rmv.university.db.ConnectionFactory;
import com.rmv.university.entity.dao.User;
import com.rmv.university.mappers.UserMapper;

import java.sql.*;
import java.util.Optional;

public class UserRepo {

  public static UserRepo INSTANCE = new UserRepo();

  public Optional<User> findByUsername(String username) {
    String command = "SELECT * FROM users WHERE username=?";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(command)) {
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (!resultSet.next()) {
        return Optional.empty();
      }

      return Optional.of(UserMapper.INSTANCE.resultSetToEntity(resultSet));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  public Optional<User> findById(Integer id) {
    String command = "SELECT * FROM users WHERE id=?";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(command)) {
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (!resultSet.next()) {
        return Optional.empty();
      }

      return Optional.of(UserMapper.INSTANCE.resultSetToEntity(resultSet));
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public User save(User user) {
    String command =
        "INSERT INTO users (username, password, student_id, lecturer_id) VALUES (?, ?, ?, ?)";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, user.getPassword());
      Integer studentId = user.getStudentId();
      Integer lecturerId = user.getLecturerId();
      if (studentId == null) {
        preparedStatement.setNull(3, java.sql.Types.NULL);
      } else {
        preparedStatement.setInt(3, user.getStudentId());
      }
      if (lecturerId == null) {
        preparedStatement.setNull(4, java.sql.Types.NULL);
      } else {
        preparedStatement.setInt(4, user.getLecturerId());
      }

      preparedStatement.executeUpdate();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

      if (generatedKeys.next()) {
        user.setId(generatedKeys.getInt(1));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return user;
  }

  public User getNames(User user) {
    String command;
    if (user.getStudentId() != null && user.getStudentId() != 0) {
      user.setStudent(true);
    }
    if (user.isStudent()) {
      command = "SELECT * FROM students WHERE id=?";
    } else {
      command = "SELECT * FROM lecturers WHERE id=?";
    }
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

      if (user.isStudent()) {
        preparedStatement.setInt(1, user.getStudentId());
      } else {
        preparedStatement.setInt(1, user.getLecturerId());
      }

      ResultSet resultSet = preparedStatement.executeQuery();

      if (!resultSet.next()) {
        return user;
      }

      user.setSurname(resultSet.getString("surname"));
      user.setFirstName(resultSet.getString("first_name"));
      user.setPatronymic(resultSet.getString("patronymic"));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return user;
  }
}
