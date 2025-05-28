package com.example.LibMan.repository;

import com.example.LibMan.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BorrowRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int lendBook(Borrow borrow) {
        String updateCopies = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0";
        int rows = jdbcTemplate.update(updateCopies, borrow.getBookId());

        if (rows == 0) throw new RuntimeException("Book is not available");

        return jdbcTemplate.update(
                "INSERT INTO borrows (member_id, book_id, borrowed_date, due_date) VALUES (?, ?, ?, ?)",
                borrow.getMemberId(), borrow.getBookId(), borrow.getBorrowedDate(), borrow.getDueDate()
        );
    }

    public int returnBook(int id) {
        Borrow borrow = findById(id);

        jdbcTemplate.update("UPDATE books SET available_copies = available_copies + 1 WHERE id = ?", borrow.getBookId());

        return jdbcTemplate.update("DELETE FROM borrows WHERE id = ?", id);
    }

    public List<Borrow> findAll() {
        return jdbcTemplate.query(
                "SELECT b.id, b.member_id, b.book_id, b.borrowed_date, b.due_date, m.name AS member_name, bk.title AS book_title " +
                        "FROM borrows b " +
                        "JOIN members m ON b.member_id = m.id " +
                        "JOIN books bk ON b.book_id = bk.id",
                (rs, rowNum) -> mapBorrow(rs)
        );
    }

    public Borrow findById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT b.id, b.member_id, b.book_id, b.borrowed_date, b.due_date, m.name AS member_name, bk.title AS book_title " +
                        "FROM borrows b " +
                        "JOIN members m ON b.member_id = m.id " +
                        "JOIN books bk ON b.book_id = bk.id " +
                        "WHERE b.id = ?",
                new Object[]{id},
                (rs, rowNum) -> mapBorrow(rs)
        );
    }

    private Borrow mapBorrow(ResultSet rs) throws SQLException {
        Borrow b = new Borrow();
        b.setId(rs.getInt("id"));
        b.setMemberId(rs.getInt("member_id"));
        b.setBookId(rs.getInt("book_id"));
        b.setBorrowedDate(rs.getDate("borrowed_date").toLocalDate());
        b.setDueDate(rs.getDate("due_date").toLocalDate());
        b.setMemberName(rs.getString("member_name"));
        b.setBookTitle(rs.getString("book_title"));
        return b;
    }
}
