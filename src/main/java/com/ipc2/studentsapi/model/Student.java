package com.ipc2.studentsapi.model;

import lombok.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class Student {
    private int id;
    private String name;
    private String email;
    private String password;
    private int age;
    private boolean registered;
}
