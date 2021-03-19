package com.rmv.university.repo;

import com.rmv.university.db.ConnectionFactory;
import com.rmv.university.entity.dao.Lecturer;
import com.rmv.university.entity.dao.Student;
import com.rmv.university.entity.dao.User;

import java.sql.*;

public class StudentRepo {
  public static StudentRepo INSTANCE = new StudentRepo();

  public Student save(User user) {
    Student student = new Student(user);

    String command = "INSERT INTO students (first_name, surname, patronymic) VALUES (?, ?, ?)";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, user.getFirstName());
      preparedStatement.setString(2, user.getSurname());
      preparedStatement.setString(3, user.getPatronymic());

      preparedStatement.executeUpdate();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

      if (generatedKeys.next()) {
        int studentId = generatedKeys.getInt(1);
        student.setId(studentId);
        user.setStudentId(studentId);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return student;
  }
}
