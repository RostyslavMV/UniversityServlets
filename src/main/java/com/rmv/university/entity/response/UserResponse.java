package com.rmv.university.entity.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String username;
    private Integer studentId;
    private Integer lecturerId;
}
