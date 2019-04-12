package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.exception.BadRequestException;
import dev.kevinyu.service.restful.exception.BaseResponseException;
import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookVO;

import java.util.List;

public interface BookService {

    List<BookVO> getList(boolean embed, String sortby, int offset, int limit, String bookName,String isbn);

    BookVO getById(String id, boolean embed);

    BookVO createBook(BookVO book);

    BookVO addAuthorToBook(String id, AuthorVO author) throws BaseResponseException;

    BookVO updateBook(String id, BookVO book);

    void deleteBook(String id) throws BaseResponseException;

    void removeAuthorFromBook(String bookId, String authorId) throws BaseResponseException;

    long count(String bookName,String isbn);
}