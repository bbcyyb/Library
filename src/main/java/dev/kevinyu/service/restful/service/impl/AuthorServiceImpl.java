package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.model.AuthorDO;
import dev.kevinyu.service.restful.repository.AuthorRepository;
import dev.kevinyu.service.restful.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository _authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository){
        this._authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDO> getList() {
        return _authorRepository.findAll();
    }

    @Override
    public AuthorDO getById(String id) {
        AuthorDO author = _authorRepository.findById(id).get();

        return author;
    }

    @Override
    public AuthorDO postNewAuthor(AuthorDO author) {
        _authorRepository.save(author);

        return author;
    }

    @Override
    public AuthorDO updateAuthor(String id, AuthorDO author) {
        boolean existed = _authorRepository.existsById(id);
        if(existed){
            return null;
        }

        _authorRepository.save(author);
        return author;
    }

    @Override
    public void deleteAuthor(String id) {
        _authorRepository.deleteById(id);
    }
}
