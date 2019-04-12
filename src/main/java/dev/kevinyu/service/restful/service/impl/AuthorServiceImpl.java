package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.exception.BadRequestException;
import dev.kevinyu.service.restful.exception.BaseResponseException;
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

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<AuthorVO> getList(boolean embed, String sortby, int offset, int limit, String authorName) {
        Pageable pageable = generatePageable(sortby, offset, limit);
        Page<AuthorDO> authorDOs;

        Example<AuthorDO> example = generateExample(authorName);
        if(example != null) {
            authorDOs = authorRepository.findAll(example, pageable);
        } else {
            authorDOs = authorRepository.findAll(pageable);
        }

        Page<AuthorVO> authorVOs = authorDOs.map(authorDO -> convertToAuthorVO(authorDO, embed));

        return authorVOs.getContent();
    }

    @Override
    public AuthorVO getById(String id, boolean embed) {
        AuthorDO authorDO = authorRepository.findById(new ObjectId(id)).get();
        AuthorVO authorVO = convertToAuthorVO(authorDO, embed);

        return authorVO;
    }

    @Override
    public AuthorVO createAuthor(AuthorVO author) {
        AuthorDO authorDO = convertToAuthorDO(author);
        authorDO = authorRepository.insert(authorDO);
        AuthorVO result = convertToAuthorVO(authorDO);

        return result;
    }

    @Override
    public AuthorVO addBookToAuthor(String id, BookVO book) throws BaseResponseException {
        if(book.getBookId().isEmpty()) {
            throw new BadRequestException("Book id cannot be empty.");
        }

        Optional<AuthorDO> optional = authorRepository.findById(new ObjectId(id));
        if(!optional.isPresent()) {
            throw new BadRequestException("Author is not present through id.");
        }

        AuthorDO authorDO = optional.get();
        List<ObjectId> bookIdList = authorDO.getBookIds();
        boolean exists = bookIdList.stream().anyMatch(bookId -> book.getBookId().equals(bookId.toString()));
        if(exists) {
            throw new BadRequestException("The book id exists in author already");
        }

        bookIdList.add(new ObjectId(book.getBookId()));
        authorDO.setBookIds(bookIdList);
        authorDO = authorRepository.save(authorDO);
        AuthorVO authorVO = convertToAuthorVO(authorDO, true);
        return authorVO;
    }

    @Override
    public AuthorVO updateAuthor(String id, AuthorVO author) {
        boolean existed = authorRepository.existsById(new ObjectId(id));
        if(!existed){
            return null;
        }

        AuthorDO authorDO = convertToAuthorDO(author);
        authorDO = authorRepository.save(authorDO);
        AuthorVO result = convertToAuthorVO(authorDO);

        return result;
    }

    @Override
    public void deleteAuthor(String id) throws BaseResponseException {
        Optional<AuthorDO> optional = authorRepository.findById(new ObjectId(id));
        if(!optional.isPresent()) {
            throw new BadRequestException("Author is not present through id.");
        }

        AuthorDO authorDO = optional.get();
        List<ObjectId> bookIdList = authorDO.getBookIds();

        if(bookIdList.isEmpty()){
            throw new BadRequestException("Cannot remove author who exists book in store");
        }

        authorRepository.deleteById(new ObjectId(id));
    }

    @Override
    public void removeBookFromAuthor(String authorId, String bookId) throws BaseResponseException {
        if(bookId.isEmpty()) {
            throw new BadRequestException("Book id cannot be empty.");
        }

        Optional<AuthorDO> optional = authorRepository.findById(new ObjectId(authorId));
        if(!optional.isPresent()) {
            throw new BadRequestException("Author is not present through id.");
        }

        AuthorDO authorDO = optional.get();
        List<ObjectId> bookIdList = authorDO.getBookIds();
        boolean exists = bookIdList.stream().anyMatch(b -> bookId.equals(b.toString()));
        if(!exists) {
            throw new BadRequestException("The book id doesn't exist in author already");
        }

        bookIdList.removeIf(b -> bookId.equals(b.toString()));
        authorDO.setBookIds(bookIdList);
        authorRepository.save(authorDO);
    }

    @Override
    public long count(String authorName) {
        long result = 0L;
        Example<AuthorDO> example = generateExample(authorName);
        if(example != null) {
            result = authorRepository.count(example);
        } else {
            result = authorRepository.count();
        }
        return result;
    }

    private AuthorVO convertToAuthorVO(AuthorDO authorDO) {
        return convertToAuthorVO(authorDO, false);
    }
    private AuthorVO convertToAuthorVO(AuthorDO authorDO, boolean embed) {
        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(authorDO, authorVO);
        authorVO.setAuthorId(authorDO.getAuthorId().toString());
        if(embed) {
            List<BookDO> bookDOs = bookRepository.findBookDOSByBookIdIn(authorDO.getBookIds());
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

    private Example<AuthorDO> generateExample(String authorName) {
        Example<AuthorDO> example = null;
        if(!authorName.isEmpty()) {
            AuthorDO authorTemp = new AuthorDO();
            authorTemp.setAuthorName(authorName);

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withIgnorePaths("bookIds")
                    .withIgnorePaths("authorId");

            example = Example.of(authorTemp, matcher);
        }

        return example;
    }
}
