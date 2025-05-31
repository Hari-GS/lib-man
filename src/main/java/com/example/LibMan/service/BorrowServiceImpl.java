package com.example.LibMan.service;

import org.springframework.stereotype.Service;

import com.example.LibMan.model.Borrow;
import com.example.LibMan.repository.BorrowRepository;
import com.example.LibMan.service.BorrowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowServiceImpl {

    @Autowired
    private BorrowRepository borrowRepository;
    private BorrowServiceImpl borrowService;

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Borrow getBorrowById(int id) {
        return borrowRepository.findById(id);
    }

    public int borrowBook(Borrow borrow) {

        return borrowRepository.lendBook(borrow);
    }

    public void returnBook(int id) {
        borrowRepository.returnBook(id);
    }

    public void deleteBorrow(int id) {
        borrowRepository.returnBook(id); // First return the book (increment copies)
        // The record will be deleted inside returnBook() already
    }
}
