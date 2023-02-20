package com.bird.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {
    private String firstName;
    private String lastName;
    @Id
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role userRole;
}
