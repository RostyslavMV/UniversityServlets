package com.rmv.university.repo;

import com.rmv.university.db.ConnectionFactory;
import com.rmv.university.entity.dao.Enrollment;
import com.rmv.university.mappers.EnrollmentMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepo {

  public static EnrollmentRepo INSTANCE = new EnrollmentRepo();

  public List<Enrollment> findEnrollmentsByStudentId(Integer studentId) {
    List<Enrollment> enrollments = new ArrayList<>();
    String command = "SELECT * FROM enrollments WHERE student_id=?";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(command)) {
      preparedStatement.setInt(1, studentId);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        enrollments.add(EnrollmentMapper.INSTANCE.resultSetToEntity(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return enrollments;
  }

  public Enrollment save(Enrollment enrollment) {
    String command =
        "INSERT INTO enrollments (student_id, course_id, mark, review) VALUES (?, ?, ?, ?)";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setInt(1, enrollment.getStudentId());
      preparedStatement.setInt(2, enrollment.getCourseId());
      preparedStatement.setNull(3, java.sql.Types.NULL);
      preparedStatement.setNull(4, java.sql.Types.NULL);

      preparedStatement.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return enrollment;
  }
}
