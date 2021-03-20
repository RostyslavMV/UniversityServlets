package com.rmv.university.mappers;

import com.rmv.university.entity.Enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentMapper {

  public static final EnrollmentMapper INSTANCE = new EnrollmentMapper();

  private EnrollmentMapper() {}

  public Enrollment resultSetToEntity(ResultSet resultSet) throws SQLException {
    Enrollment enrollment = new Enrollment();
    enrollment.setStudentId(resultSet.getInt("student_id"));
    enrollment.setCourseId(resultSet.getInt("course_id"));
    String markString = resultSet.getString("mark");
    if(markString == null){
      enrollment.setMark(null);
    }
    else {
      enrollment.setMark(Integer.parseInt(markString));
    }
    enrollment.setReview(resultSet.getString("review"));

    return enrollment;
  }
}
