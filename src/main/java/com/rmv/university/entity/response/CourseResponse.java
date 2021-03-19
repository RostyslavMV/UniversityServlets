package com.rmv.university.entity.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponse {
    private Integer id;
    private String name;
    private String surname;
    private String firstName;
    private String patronymic;

    private Integer mark;
    private String review;
}
