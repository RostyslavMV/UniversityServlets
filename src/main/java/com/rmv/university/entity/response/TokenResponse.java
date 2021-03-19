package com.rmv.university.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private String role;
    private String firstName;
    private String surname;
}
