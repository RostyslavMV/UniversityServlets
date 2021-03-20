package com.rmv.university.repo;

import com.rmv.university.db.ConnectionFactory;
import com.rmv.university.entity.Course;
import com.rmv.university.mappers.CourseMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepo {

  public static final CourseRepo INSTANCE = new CourseRepo();

  public Optional<Course> findById(Integer id) {
    String command = "SELECT * FROM courses c INNER JOIN lecturers l on l.id = c.lecturer_id WHERE c.id=?";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(command)) {
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (!resultSet.next()) {
        return Optional.empty();
      }

      return Optional.of(CourseMapper.INSTANCE.resultSetToEntityWithLecturerName(resultSet));
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public List<Course> findAll() {
    List<Course> courses = new ArrayList<>();
    String command = "SELECT * FROM courses INNER JOIN lecturers l on l.id = courses.lecturer_id";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(command)) {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        courses.add(CourseMapper.INSTANCE.resultSetToEntityWithLecturerName(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return courses;
  }

  public List<Course> findAllCoursesForLecturer(Integer lecturerId) {
    List<Course> courses = new ArrayList<>();
    String command = "SELECT * FROM courses WHERE lecturer_id=?";
    try (Connection connection = ConnectionFactory.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(command)) {
      preparedStatement.setInt(1, lecturerId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        courses.add(CourseMapper.INSTANCE.resultSetToEntity(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return courses;
  }

  public Course save(Course course) {
    String command = "INSERT INTO courses (name, lecturer_id) VALUES (?, ?)";
    try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, course.getName());
      preparedStatement.setInt(2, course.getLecturerId());

      preparedStatement.executeUpdate();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

      if (generatedKeys.next()) {
        course.setId(generatedKeys.getInt(1));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return course;
  }
}
