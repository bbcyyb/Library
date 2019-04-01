package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.AuthorDO;

import java.util.List;

public interface AuthorService {
    List<AuthorDO> getList();

    AuthorDO getById(String id);

    AuthorDO post(AuthorDO author);

    AuthorDO update(String id, AuthorDO author);

    void delete(String id);
}
