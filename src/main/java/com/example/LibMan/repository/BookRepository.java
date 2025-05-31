package com.example.LibMan.repository;

// --- BookRepository.java ---

import com.example.LibMan.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", this::mapRow);
    }

    public Book findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM books WHERE id = ?", this::mapRow, id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books(title, author, isbn, published_date, available_copies) VALUES (?, ?, ?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies());
    }

    public void update(Book book) {
        jdbcTemplate.update("UPDATE books SET title=?, author=?, isbn=?, published_date=?, available_copies=? WHERE id=?",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies(), book.getId());
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }

    public List<Book> search(String keyword) {
        String q = "%" + keyword + "%";
        return jdbcTemplate.query("SELECT * FROM books WHERE title ILIKE ? OR author ILIKE ? OR isbn ILIKE ?",
                this::mapRow, q, q, q);
    }

    private Book mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
        Book b = new Book();
        b.setId(rs.getLong("id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setIsbn(rs.getString("isbn"));
        b.setPublishedDate(rs.getDate("published_date").toLocalDate());
        b.setAvailableCopies(rs.getInt("available_copies"));
        return b;
    }
}
