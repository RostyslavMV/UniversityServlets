package com.rmv.university.entity;

import lombok.Data;

@Data
public class Enroll {

    private Integer studentId;
    private Integer courseId;

    private Integer mark;
    private String review;

}
