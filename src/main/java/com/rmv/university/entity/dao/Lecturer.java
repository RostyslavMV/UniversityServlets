package com.rmv.university.entity.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Lecturer {

  private Integer id;

  private String surname;
  private String firstName;
  private String patronymic;

  public Lecturer(User user) {
    this.surname = user.getSurname();
    this.firstName = user.getFirstName();
    this.patronymic = user.getPatronymic();
  }
}
