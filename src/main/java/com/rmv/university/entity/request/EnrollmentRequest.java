package com.rmv.university.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentRequest {
    private Integer studentId;
    private Integer courseId;
    private Integer mark;
    private String review;
}
