package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.BookDO;

import java.util.List;

public interface BookService {

    List<BookDO> getList();

    BookDO getById(String id);

    BookDO post(BookDO book);

    BookDO update(String id, BookDO book);

    void delete(String id);
}