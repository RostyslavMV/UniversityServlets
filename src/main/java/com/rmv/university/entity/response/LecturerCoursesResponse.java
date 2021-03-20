package com.rmv.university.entity.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LecturerCoursesResponse {
    private List<LecturerCourseResponse> courseResponses;
}
