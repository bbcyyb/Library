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

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookVO> getList(boolean embed, String sortby, int offset, int limit, String bookName, String isbn) {
        Pageable pageable = generatePageable(sortby, offset, limit);
        Page<BookDO> bookDOs;

        Example<BookDO> example = generateExample(bookName, isbn);
        if(example != null) {
            bookDOs = bookRepository.findAll(example, pageable);
        } else {
            bookDOs = bookRepository.findAll(pageable);
        }

        Page<BookVO> bookVOs = bookDOs.map(bookDO -> convertToBookVO(bookDO, embed));

        return bookVOs.getContent();
    }

    @Override
    public BookVO getById(String id, boolean embed) {
        BookDO bookDO = bookRepository.findById(new ObjectId(id)).get();
        BookVO bookVO = convertToBookVO(bookDO,embed);

        return bookVO;
    }

    @Override
    public BookVO createBook(BookVO book) {
        BookDO bookDO = convertToBookDO(book);
        bookDO = bookRepository.insert(bookDO);
        BookVO result = convertToBookVO(bookDO);

        return result;
    }

    @Override
    public BookVO addAuthorToBook(String id, AuthorVO author) {
        //TODO:
        return null;
    }

    @Override
    public BookVO updateBook(String id, BookVO book) {
        boolean existed = bookRepository.existsById(new ObjectId(id));
        if(!existed){
            return null;
        }

        BookDO bookDO = convertToBookDO(book);
        bookDO = bookRepository.save(bookDO);
        BookVO result = convertToBookVO(bookDO);

        return result;
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(new ObjectId(id));
    }

    @Override
    public void removeAuthorFromBook(String bookId, String authorId) {
        //TODO:
    }

    @Override
    public long count(String bookName, String isbn) {
        long result = 0L;
        Example<BookDO> example = generateExample(bookName, isbn);
        if(example != null) {
            result = bookRepository.count(example);
        } else {
            result = bookRepository.count();
        }

        return result;
    }

    private BookVO convertToBookVO(BookDO bookDO){
        return convertToBookVO(bookDO, false);
    }

    private BookVO convertToBookVO(BookDO bookDO, boolean embed) {
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookDO, bookVO);
        bookVO.setBookId(bookDO.getBookId().toString());
        if(embed) {
            List<AuthorDO> authorDOs = authorRepository.findAuthorDOSByAuthorIdIn(bookDO.getAuthorIds());
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

    private Example<BookDO> generateExample(String bookName, String isbn) {
        Example<BookDO> example = null;
        if(!bookName.isEmpty() || !isbn.isEmpty()) {
            BookDO bookTemp = new BookDO();
            bookTemp.setBookName(bookName);
            bookTemp.setISBN(isbn);

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withIgnorePaths("authorIds")
                    .withIgnorePaths("bookId");

            example = Example.of(bookTemp, matcher);
        }

        return example;
    }
}
