package com.example.LibMan.Controller;

import com.example.LibMan.model.Member;
import com.example.LibMan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<Void> addMember(@RequestBody Member member) {
        memberRepository.create(member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable int id) {
        return memberRepository.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable int id, @RequestBody Member member) {
        memberRepository.update(id, member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        memberRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
