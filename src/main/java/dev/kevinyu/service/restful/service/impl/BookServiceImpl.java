package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.model.AuthorDO;
import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookDO;
import dev.kevinyu.service.restful.model.BookVO;
import dev.kevinyu.service.restful.repository.AuthorRepository;
import dev.kevinyu.service.restful.repository.BookRepository;
import dev.kevinyu.service.restful.service.BookService;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl extends BaseServiceImpl implements BookService {

    private BookRepository _bookRepository;
    private AuthorRepository _authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository){
        this._bookRepository = bookRepository;
        this._authorRepository = authorRepository;
    }

    @Override
    public List<BookVO> getList(boolean embed, String sortby, int offset, int limit, String bookName, String isbn) {
        Pageable pageable = generatePageable(sortby, offset, limit);
        Page<BookDO> bookDOs = null;

        if(!bookName.isEmpty() || !isbn.isEmpty()) {
            BookDO bookTemp = new BookDO();
            bookTemp.setBookName(bookName);
            bookTemp.setISBN(isbn);

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withIgnorePaths("authorIds")
                    .withIgnorePaths("bookId");

            Example<BookDO> example = Example.of(bookTemp, matcher);
            bookDOs = _bookRepository.findAll(example, pageable);
        } else {
            bookDOs = _bookRepository.findAll(pageable);
        }

        Page<BookVO> bookVOs = bookDOs.map(bookDO -> convertToBookVO(bookDO, embed));

        return bookVOs.getContent();
    }

    @Override
    public BookVO getById(String id, boolean embed) {
        BookDO bookDO = _bookRepository.findById(new ObjectId(id)).get();
        BookVO bookVO = convertToBookVO(bookDO,embed);

        return bookVO;
    }

    @Override
    public BookVO createBook(BookVO book) {
        BookDO bookDO = convertToBookDO(book);
        bookDO = _bookRepository.insert(bookDO);
        BookVO result = convertToBookVO(bookDO);

        return result;
    }

    @Override
    public BookVO addAuthorToBook(String id, AuthorVO author) {
        return null;
    }

    @Override
    public BookVO updateBook(String id, BookVO book) {
        boolean existed = _bookRepository.existsById(new ObjectId(id));
        if(!existed){
            return null;
        }

        BookDO bookDO = convertToBookDO(book);
        bookDO = _bookRepository.save(bookDO);
        BookVO result = convertToBookVO(bookDO);

        return result;
    }

    @Override
    public void deleteBook(String id) {
        _bookRepository.deleteById(new ObjectId(id));
    }

    @Override
    public void removeAuthorFromBook(String bookId, String authorId) {

    }

    private BookVO convertToBookVO(BookDO bookDO){
        return convertToBookVO(bookDO, false);
    }

    private BookVO convertToBookVO(BookDO bookDO, boolean embed) {
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookDO, bookVO);
        bookVO.setBookId(bookDO.getBookId().toString());
        if(embed) {
            List<AuthorDO> authorDOs = _authorRepository.findAuthorDOSByAuthorIdIn(bookDO.getAuthorIds());
            List<AuthorVO> authorVOs = authorDOs.stream().map(authorDO -> {
                AuthorVO authorVO = new AuthorVO();
                BeanUtils.copyProperties(authorDO, authorVO);
                authorVO.setAuthorId(authorDO.getAuthorId().toString());
                return authorVO;
            }).collect(Collectors.toList());

            bookVO.setAuthors(authorVOs);
        }

        return bookVO;
    }

    private BookDO convertToBookDO(BookVO bookVO){
        BookDO bookDO = new BookDO();
        BeanUtils.copyProperties(bookVO, bookDO);
        String bookId = bookVO.getBookId();
        if(bookId != null && !bookId.isEmpty() ) {
            bookDO.setBookId(new ObjectId(bookVO.getBookId()));
        }
        List<ObjectId> authorIds = bookVO.getAuthors().stream().map(authorVO -> new ObjectId(authorVO.getAuthorId())).collect(Collectors.toList());
        bookDO.setAuthorIds(authorIds);
        return bookDO;
    }
}
