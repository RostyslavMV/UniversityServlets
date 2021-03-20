package com.rmv.university.entity;

import lombok.Data;

@Data
public class Enrollment {

    private Integer studentId;
    private Integer courseId;

    private Integer mark;
    private String review;

}
