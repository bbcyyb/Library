package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.AuthorVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {
    List<AuthorVO> getList(boolean embed, String sortby, int offset, int limit);

    AuthorVO getById(String id, boolean embed);

    AuthorVO post(AuthorVO author);

    AuthorVO update(String id, AuthorVO author);

    void delete(String id);
}
