package com.rmv.university.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper {
  public static StudentMapper INSTANCE = new StudentMapper();

  private StudentMapper() {}

  public String resultSetToName(ResultSet resultSet) throws SQLException {
    String name = "";
    name += resultSet.getString("surname") + " ";
    name += resultSet.getString("first_name")+" ";
    name += resultSet.getString("patronymic");
    return name;
  }
}
