package com.rmv.university.repo;

import com.rmv.university.db.ConnectionFactory;
import com.rmv.university.entity.dao.Lecturer;
import com.rmv.university.entity.dao.User;

import java.sql.*;

public class LecturerRepo {
  public static LecturerRepo INSTANCE = new LecturerRepo();

  public Lecturer save(User user) {
    Lecturer lecturer = new Lecturer(user);

    String command = "INSERT INTO lecturers (first_name, surname, patronymic) VALUES (?, ?, ?)";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, user.getFirstName());
      preparedStatement.setString(2, user.getSurname());
      preparedStatement.setString(3, user.getPatronymic());

      preparedStatement.executeUpdate();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

      if (generatedKeys.next()) {
        int lecturerId = generatedKeys.getInt(1);
        lecturer.setId(lecturerId);
        user.setLecturerId(lecturerId);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return lecturer;
  }
}
