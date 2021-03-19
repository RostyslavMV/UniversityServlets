package com.rmv.university.entity.dao;

import lombok.Data;

@Data
public class User {

  private Integer id;

  private String username;
  private String password;

  private String surname;
  private String firstName;
  private String patronymic;

  private Integer studentId;
  private Integer lecturerId;

  private boolean isStudent;

  public String getRole(){
    return isStudent? "student" : "lecturer";
  }
}
