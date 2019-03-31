package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.AuthorDO;

import java.util.List;

public interface AuthorService {
    List<AuthorDO> getList();


    AuthorDO getById(String id);

    AuthorDO postNewAuthor(AuthorDO author);

    AuthorDO updateAuthor(String id, AuthorDO author);

    void deleteAuthor(String id);
}
