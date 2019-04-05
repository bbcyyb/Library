package dev.kevinyu.service.restful.controller;

import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookVO;
import dev.kevinyu.service.restful.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Api("BookController")
public class BookController {

    private BookService _bookService;

    @Autowired
    public BookController(BookService bookService) {
        _bookService = bookService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation("List all books.")
    public List<BookVO> getBookList(@RequestParam(value="embed",required = false, defaultValue="false") boolean embed,
                                    @RequestParam(value="sortby", required = false, defaultValue="") String sortby,
                                    @RequestParam(value="offset", required = false, defaultValue="0") int offset,
                                    @RequestParam(value="limit", required = false, defaultValue="0") int limit,
                                    @RequestParam(value="bookName", required = false, defaultValue="") String bookName,
                                    @RequestParam(value="isbn", required = false, defaultValue="") String isbn){
        return _bookService.getList(embed, sortby, offset, limit, bookName, isbn);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Retrieve an entire book object.")
    public BookVO getBooks(@PathVariable String id, @RequestParam(value="embed",required = false, defaultValue="false")boolean embed){
        return _bookService.getById(id, embed);
    }

    @RequestMapping(value = "/{id}/authors", method = RequestMethod.GET)
    @ApiOperation("Retrieve author list of book.")
    public List<AuthorVO> getAuthorsByBookId(@PathVariable String id){
        BookVO bookVO = _bookService.getById(id, true);
        return bookVO.getAuthors();
    }

    @RequestMapping(value = "/{id}/authors", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add an author to a specific book author list.")
    public BookVO addAuthorToBook(@PathVariable String id){
        return _bookService.addAuthorToBook(id, null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add a new book.")
    public BookVO createBook(@RequestBody BookVO book){
        return _bookService.createBook(book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation("Update book (entire object).")
    public BookVO updateBook(@PathVariable String id, @RequestBody BookVO book){
        return _bookService.updateBook(id, book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ApiOperation("Update book (partial object).")
    public BookVO updatePartialBook(@PathVariable String id, @RequestBody BookVO book){
        //TODO: Write a new method to partial update author object.
        return _bookService.updateBook(id, book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete book.")
    public void deleteBook(@PathVariable String id){
        _bookService.deleteBook(id);
    }

    @RequestMapping(value = "/{bookId}/authors/{authorId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Remove an author from a specific book author list.")
    public void removeAuthorFromBook(@PathVariable String bookId, @PathVariable String authorId){
        _bookService.removeAuthorFromBook(bookId, authorId);
    }
}
