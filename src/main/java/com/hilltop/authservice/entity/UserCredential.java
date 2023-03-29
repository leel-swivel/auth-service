package com.hilltop.authservice.entity;


import com.hilltop.authservice.domain.request.UserCredentialRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential {
    @Transient
    private static final String USER_ID_PREFIX = "uid-";

    @Id
    private String id;
    private String name;
    private String email;
    private String password;


    public UserCredential(UserCredentialRequestDto userCredentialRequestDto) {
        this.id = USER_ID_PREFIX + UUID.randomUUID();
        this.name = userCredentialRequestDto.getName();
        this.email = userCredentialRequestDto.getEmail();
        this.password = userCredentialRequestDto.getPassword();
    }
}
