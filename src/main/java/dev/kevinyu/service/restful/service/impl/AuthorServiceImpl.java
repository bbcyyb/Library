package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.model.AuthorDO;
import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookDO;
import dev.kevinyu.service.restful.model.BookVO;
import dev.kevinyu.service.restful.repository.AuthorRepository;
import dev.kevinyu.service.restful.repository.BookRepository;
import dev.kevinyu.service.restful.service.AuthorService;
import jdk.jfr.events.ExceptionThrownEvent;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl extends BaseServiceImpl implements AuthorService {

    private AuthorRepository _authorRepository;
    private BookRepository _bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository){
        this._authorRepository = authorRepository;
        this._bookRepository = bookRepository;
    }

    @Override
    public List<AuthorVO> getList(boolean embed, String sortby, int offset, int limit, String authorName) {
        Pageable pageable = generatePageable(sortby, offset, limit);
        Page<AuthorDO> authorDOs = null;
        if(!authorName.isEmpty()) {
            AuthorDO authorTemp = new AuthorDO();
            authorTemp.setAuthorName(authorName);

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withIgnorePaths("bookIds")
                    .withIgnorePaths("authorId");

            Example<AuthorDO> example = Example.of(authorTemp, matcher);
            authorDOs = _authorRepository.findAll(example, pageable);
        } else {
            authorDOs = _authorRepository.findAll(pageable);
        }

        Page<AuthorVO> authorVOs = authorDOs.map(authorDO -> convertToAuthorVO(authorDO, embed));

        return authorVOs.getContent();
    }

    @Override
    public AuthorVO getById(String id, boolean embed) {
        AuthorDO authorDO = _authorRepository.findById(new ObjectId(id)).get();
        AuthorVO authorVO = convertToAuthorVO(authorDO, embed);

        return authorVO;
    }

    @Override
    public AuthorVO createAuthor(AuthorVO author) {
        AuthorDO authorDO = convertToAuthorDO(author);
        authorDO = _authorRepository.insert(authorDO);
        AuthorVO result = convertToAuthorVO(authorDO);

        return result;
    }

    @Override
    public AuthorVO addBookToAuthor(String id, BookVO book) {
        AuthorDO authorDO = null;
        Optional<AuthorDO> optional = _authorRepository.findById(new ObjectId(id));
        if(optional.isPresent()) {
            return null;
        }
        //TODO:
        return null;
    }

    @Override
    public AuthorVO updateAuthor(String id, AuthorVO author) {
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
    public void deleteAuthor(String id) {
        _authorRepository.deleteById(new ObjectId(id));
    }

    @Override
    public void removeBookFromAuthor(String authorId, String bookId) {

    }

    private AuthorVO convertToAuthorVO(AuthorDO authorDO) {
        return convertToAuthorVO(authorDO, false);
    }
    private AuthorVO convertToAuthorVO(AuthorDO authorDO, boolean embed) {
        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(authorDO, authorVO);
        authorVO.setAuthorId(authorDO.getAuthorId().toString());
        if(embed) {
            List<BookDO> bookDOs = _bookRepository.findBookDOSByBookIdIn(authorDO.getBookIds());
            List<BookVO> bookVOs = bookDOs.stream().map(bookDO -> {
                BookVO bookVO = new BookVO();
                BeanUtils.copyProperties(bookDO, bookVO);
                bookVO.setBookId(bookDO.getBookId().toString());
                return bookVO;
            }).collect(Collectors.toList());
            authorVO.setBooks(bookVOs);
        }

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
