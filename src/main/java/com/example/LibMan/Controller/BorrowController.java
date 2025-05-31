package com.example.LibMan.Controller;

import com.example.LibMan.model.Borrow;
import com.example.LibMan.service.BorrowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowServiceImpl borrowService;

    // Get all borrow records (with joined member and book details)
    @GetMapping
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

    // Get a borrow record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Borrow> getBorrowById(@PathVariable int id) {
        return ResponseEntity.ok(borrowService.getBorrowById(id));
    }

    // Borrow a book (if availableCopies > 0)
    @PostMapping
    public ResponseEntity<String> borrowBook(@RequestBody Borrow borrow) {
        if(borrowService.borrowBook(borrow)==-1){
            return ResponseEntity.ok("Book Not Available.");
        }
        return ResponseEntity.ok("Book borrowed successfully.");
    }

    // Return a book (increment availableCopies and update borrow info)
    @PutMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable int id) {
        borrowService.returnBook(id);
        return ResponseEntity.ok("Book returned successfully.");
    }

    // Delete a borrow record
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorrow(@PathVariable int id) {
        borrowService.deleteBorrow(id);
        return ResponseEntity.ok("Borrow record deleted.");
    }
}
