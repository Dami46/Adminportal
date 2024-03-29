package com.adminportal.Service.Impl;

import com.adminportal.Domain.Book;
import com.adminportal.Repository.BookRepository;
import com.adminportal.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public List<Book> findAllForDrop() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Book> activeBookList = new ArrayList<>();

        for (Book book: bookList) {
            if(book.isActive() && book.isBookToDrop()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void removeOne(Long id) {
        bookRepository.deleteById(id);
    }

}
