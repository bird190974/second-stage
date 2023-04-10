package com.bird.dto;

import com.bird.entity.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
    ClientReadDto client;
}
