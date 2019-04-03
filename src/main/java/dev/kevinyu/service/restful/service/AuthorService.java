package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.AuthorVO;

import java.util.List;

public interface AuthorService {
    List<AuthorVO> getList(boolean embed);

    AuthorVO getById(String id, boolean embed);

    AuthorVO post(AuthorVO author);

    AuthorVO update(String id, AuthorVO author);

    void delete(String id);
}
