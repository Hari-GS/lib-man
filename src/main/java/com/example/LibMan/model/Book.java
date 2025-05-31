package com.example.LibMan.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishedDate;
    private int availableCopies;

    // Getters and Setters
}