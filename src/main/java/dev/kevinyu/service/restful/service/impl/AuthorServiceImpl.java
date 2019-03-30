package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.repository.AuthorRepository;
import dev.kevinyu.service.restful.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository _authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository){
        this._authorRepository = authorRepository;
    }

    @Override
    public void getList() {

    }

    @Override
    public void getById() {

    }
}
