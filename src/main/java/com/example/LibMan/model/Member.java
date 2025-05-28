package com.example.LibMan.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Member {
    private Long id;
    private String name;
    private String phone;
    private LocalDate registeredDate;

    // Getters and Setters
}
