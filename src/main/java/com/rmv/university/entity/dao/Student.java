package com.rmv.university.entity.dao;

import lombok.Data;

@Data
public class Student {

  private Integer id;

  private String surname;
  private String firstName;
  private String patronymic;

  public Student(User user) {
    this.surname = user.getSurname();
    this.firstName = user.getFirstName();
    this.patronymic = user.getPatronymic();
  }
}
