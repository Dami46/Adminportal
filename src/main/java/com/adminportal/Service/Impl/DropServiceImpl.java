package com.adminportal.Service.Impl;

import com.adminportal.Domain.Book;
import com.adminportal.Domain.DropItem;
import com.adminportal.Repository.BookRepository;
import com.adminportal.Repository.DropRepository;
import com.adminportal.Service.DropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DropServiceImpl implements DropService {

    @Autowired
    private DropRepository dropRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<DropItem> findAll() {
        List<DropItem> dropItemList = (List<DropItem>) dropRepository.findAll();
        for(DropItem dropItem : dropItemList) {
            Optional<Book> book = bookRepository.findById(dropItem.getBookId());
            book.ifPresent(dropItem::setBook);
        }
        return dropItemList;
    }

    @Override
    public Optional<DropItem> findById(Long id) {
        return dropRepository.findById(id);
    }

    @Override
    public DropItem save(DropItem dropItem) {
        return dropRepository.save(dropItem);
    }

    @Override
    public void removeOne(Long id) {
        dropRepository.deleteById(id);
    }

}
