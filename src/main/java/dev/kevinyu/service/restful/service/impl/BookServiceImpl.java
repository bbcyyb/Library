package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.repository.BookRepository;
import dev.kevinyu.service.restful.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository _bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this._bookRepository = bookRepository;
    }

    @Override
    public void getList() {

    }

    @Override
    public void getById() {

    }
}
