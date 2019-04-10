package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookVO;

import java.util.List;

public interface BookService {

    List<BookVO> getList(boolean embed, String sortby, int offset, int limit, String bookName,String isbn);

    BookVO getById(String id, boolean embed);

    BookVO createBook(BookVO book);

    BookVO addAuthorToBook(String id, AuthorVO author);

    BookVO updateBook(String id, BookVO book);

    void deleteBook(String id);

    void removeAuthorFromBook(String bookId, String authorId);

    long count(String bookName,String isbn);
}