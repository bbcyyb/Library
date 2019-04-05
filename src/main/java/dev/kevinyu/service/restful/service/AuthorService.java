package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookVO;

import java.util.List;

public interface AuthorService {
    List<AuthorVO> getList(boolean embed, String sortby, int offset, int limit, String authorName);

    AuthorVO getById(String id, boolean embed);

    AuthorVO createAuthor(AuthorVO author);

    AuthorVO addBookToAuthor(String id, BookVO book);

    AuthorVO updateAuthor(String id, AuthorVO author);

    void deleteAuthor(String id);

    void removeBookFromAuthor(String authorId, String bookId);
}
