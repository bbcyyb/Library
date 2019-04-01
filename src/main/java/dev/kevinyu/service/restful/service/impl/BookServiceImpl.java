package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.model.BookDO;
import dev.kevinyu.service.restful.repository.AuthorRepository;
import dev.kevinyu.service.restful.repository.BookRepository;
import dev.kevinyu.service.restful.service.BookService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository _bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this._bookRepository = bookRepository;
    }

    @Override
    public List<BookDO> getList() {
        return _bookRepository.findAll();
    }

    @Override
    public BookDO getById(String id) {
        BookDO book = _bookRepository.findById(new ObjectId(id)).get();

        return book;
    }

    @Override
    public BookDO post(BookDO book) {
        _bookRepository.insert(book);

        return book;
    }

    @Override
    public BookDO update(String id, BookDO book) {
        boolean existed = _bookRepository.existsById(new ObjectId(id));
        if(existed){
            return null;
        }

        _bookRepository.save(book);
        return book;
    }

    @Override
    public void delete(String id) {
        _bookRepository.deleteById(new ObjectId(id));
    }
}
