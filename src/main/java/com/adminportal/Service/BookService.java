package com.adminportal.Service;

import com.adminportal.Domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    void removeOne(Long id);
}
