package com.rmv.university.entity.dao;

import lombok.Data;

@Data
public class Course {

    private Integer id;

    private String name;
    private Integer lecturerId;

    private Lecturer lecturer;
}
