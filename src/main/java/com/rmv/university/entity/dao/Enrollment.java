package com.rmv.university.entity.dao;

import lombok.Data;

@Data
public class Enrollment {

    private Integer studentId;
    private Integer courseId;

    private Integer mark;
    private String review;

}
