package com.example.LibMan.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Borrow {
    private int id;
    private int memberId;
    private int bookId;
    private LocalDate borrowedDate;
    private LocalDate dueDate;

    // Extended response
    private String memberName;
    private String bookTitle;

    // Getters and Setter
}