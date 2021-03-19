package com.rmv.university.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String username;
    private String password;
    private String surname;
    private String firstName;
    private String patronymic;
    private String role;
}
