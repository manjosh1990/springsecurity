package com.manjosh.practice.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
   // @NotEmpty
    private String firstName;

   // @NotEmpty
    private String lastName;

   // @NotEmpty
    private String email;

   // @NotEmpty
    private char[] password;
}
