package com.hilltop.authservice.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialRequestDto {

    private String name;
    private String email;
    private String password;
}
