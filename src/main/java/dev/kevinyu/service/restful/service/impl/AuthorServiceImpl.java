package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.model.AuthorDO;
import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookDO;
import dev.kevinyu.service.restful.model.BookVO;
import dev.kevinyu.service.restful.repository.AuthorRepository;
import dev.kevinyu.service.restful.repository.BookRepository;
import dev.kevinyu.service.restful.service.AuthorService;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository _authorRepository;
    private BookRepository _bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository){
        this._authorRepository = authorRepository;
        this._bookRepository = bookRepository;
    }

    @Override
    public List<AuthorVO> getList() {
        List<AuthorDO> Authors = _authorRepository.findAll();
        List<AuthorVO> result = Authors.stream().map(AuthorDO -> convertToAuthorVO(AuthorDO)).collect(Collectors.toList());

        return result;
    }

    @Override
    public AuthorVO getById(String id) {
        AuthorDO authorDO = _authorRepository.findById(new ObjectId(id)).get();
        AuthorVO authorVO = convertToAuthorVO(authorDO);

        return authorVO;
    }

    @Override
    public AuthorVO post(AuthorVO author) {
        AuthorDO authorDO = convertToAuthorDO(author);
        authorDO = _authorRepository.insert(authorDO);
        AuthorVO result = convertToAuthorVO(authorDO);

        return result;
    }

    @Override
    public AuthorVO update(String id, AuthorVO author) {
        boolean existed = _authorRepository.existsById(new ObjectId(id));
        if(!existed){
            return null;
        }

        AuthorDO authorDO = convertToAuthorDO(author);
        authorDO = _authorRepository.save(authorDO);
        AuthorVO result = convertToAuthorVO(authorDO);

        return result;
    }

    @Override
    public void delete(String id) {
        _authorRepository.deleteById(new ObjectId(id));
    }

    private AuthorVO convertToAuthorVO(AuthorDO authorDO) {
        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(authorDO, authorVO);
        authorVO.setAuthorId(authorDO.getAuthorId().toString());
        List<BookDO> bookDOs = _bookRepository.findBookDOSByBookIdIn(authorDO.getBookIds());
        List<BookVO> bookVOs = bookDOs.stream().map(bookDO -> {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(bookDO, bookVO);
            bookVO.setBookId(bookDO.getBookId().toString());
            return bookVO;
        }).collect(Collectors.toList());
        authorVO.setBooks(bookVOs);

        return authorVO;
    }

    private AuthorDO convertToAuthorDO(AuthorVO authorVO){
        AuthorDO authorDO = new AuthorDO();
        BeanUtils.copyProperties(authorVO, authorDO);
        String authorId = authorVO.getAuthorId();
        if(authorId != null && !authorId.isEmpty() ) {
            authorDO.setAuthorId(new ObjectId(authorVO.getAuthorId()));
        }
        List<ObjectId> bookIds = authorVO.getBooks().stream().map(bookVO -> new ObjectId(bookVO.getBookId())).collect(Collectors.toList());
        authorDO.setBookIds(bookIds);
        return authorDO;
    }
}
