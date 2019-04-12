package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.exception.BaseResponseException;
import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookVO;

import java.util.List;

public interface AuthorService {
    List<AuthorVO> getList(boolean embed, String sortby, int offset, int limit, String authorName);

    AuthorVO getById(String id, boolean embed);

    AuthorVO createAuthor(AuthorVO author);

    AuthorVO addBookToAuthor(String id, BookVO book) throws BaseResponseException;

    AuthorVO updateAuthor(String id, AuthorVO author);

    void deleteAuthor(String id) throws BaseResponseException;

    void removeBookFromAuthor(String authorId, String bookId) throws BaseResponseException;

    long count(String authorName);
}
