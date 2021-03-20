package com.rmv.university.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerCourseResponse {
    private Integer courseId;
    private String courseName;
    private List<LecturerEnrollmentResponse> lecturerEnrollmentResponses;
}
