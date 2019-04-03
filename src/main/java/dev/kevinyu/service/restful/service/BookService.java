package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.BookVO;

import java.util.List;

public interface BookService {

    List<BookVO> getList(boolean embed);

    BookVO getById(String id, boolean embed);

    BookVO post(BookVO book);

    BookVO update(String id, BookVO book);

    void delete(String id);
}