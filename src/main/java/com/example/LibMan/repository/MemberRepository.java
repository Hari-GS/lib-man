package com.example.LibMan.repository;

import com.example.LibMan.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int create(Member member) {
        return jdbcTemplate.update(
                "INSERT INTO members (name, phone, registered_date) VALUES (?, ?, ?)",
                member.getName(), member.getPhone(), member.getRegisteredDate()
        );
    }

    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM members",
                (rs, rowNum) -> mapMember(rs));
    }

    public Member findById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM members WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> mapMember(rs)
        );
    }

    public int update(int id, Member member) {
        return jdbcTemplate.update(
                "UPDATE members SET name = ?, phone = ?, registered_date = ? WHERE id = ?",
                member.getName(), member.getPhone(), member.getRegisteredDate(), id
        );
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM members WHERE id = ?", id);
    }

    private Member mapMember(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setId((long) rs.getInt("id"));
        m.setName(rs.getString("name"));
        m.setPhone(rs.getString("phone"));
        m.setRegisteredDate(rs.getDate("registered_date").toLocalDate());
        return m;
    }
}
