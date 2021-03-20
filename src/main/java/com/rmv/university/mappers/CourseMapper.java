package com.rmv.university.mappers;

import com.rmv.university.entity.Course;
import com.rmv.university.entity.Enrollment;
import com.rmv.university.entity.Lecturer;
import com.rmv.university.entity.response.CourseResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper {

  public static final CourseMapper INSTANCE = new CourseMapper();

  private CourseMapper() {}

  public Course resultSetToEntityWithLecturerName(ResultSet resultSet) throws SQLException {
    Course course = resultSetToEntity(resultSet);
    Lecturer lecturer = new Lecturer();
    lecturer.setFirstName(resultSet.getString("first_name"));
    lecturer.setSurname(resultSet.getString("surname"));
    lecturer.setPatronymic(resultSet.getString("patronymic"));
    course.setLecturer(lecturer);
    return course;
  }

  public Course resultSetToEntity(ResultSet resultSet) throws SQLException {
    Course course = new Course();
    course.setId(resultSet.getInt("id"));
    course.setName(resultSet.getString("name"));
    course.setLecturerId(resultSet.getInt("lecturer_id"));
    return course;
  }

  public CourseResponse courseToCourseResponse(Course course) {
    CourseResponse courseResponse = new CourseResponse();
    courseResponse.setId(course.getId());
    courseResponse.setName(course.getName());
    courseResponse.setFirstName(course.getLecturer().getFirstName());
    courseResponse.setSurname(course.getLecturer().getSurname());
    courseResponse.setPatronymic(course.getLecturer().getPatronymic());
    return courseResponse;
  }

  public CourseResponse courseAndEnrollmentToCourseResponse(Course course, Enrollment enrollment) {
    CourseResponse courseResponse = courseToCourseResponse(course);
    courseResponse.setMark(enrollment.getMark());
    courseResponse.setReview(enrollment.getReview());
    return courseResponse;
  }
}
