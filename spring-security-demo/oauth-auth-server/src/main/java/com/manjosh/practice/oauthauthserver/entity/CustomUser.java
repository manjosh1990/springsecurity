package com.manjosh.practice.oauthauthserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_table")
@Data
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Column(length = 60)
    private String password;

    private String role;
    private boolean enabled = false;
}
