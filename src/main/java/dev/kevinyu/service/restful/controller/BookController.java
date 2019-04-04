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
                                    @RequestParam(value="limit", required = false, defaultValue="0") int limit){
        return _bookService.getList(embed, sortby, offset, limit);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Retrieve an entire book object.")
    public BookVO getBook(@PathVariable String id, @RequestParam(value="embed",required = false, defaultValue="false")boolean embed){
        return _bookService.getById(id, embed);
    }

    @RequestMapping(value = "/{id}/authors", method = RequestMethod.GET)
    @ApiOperation("Retrieve author list of book.")
    public List<AuthorVO> getAuthorsByBookId(@PathVariable String id){
        BookVO bookVO = _bookService.getById(id, true);
        return bookVO.getAuthors();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add a new book.")
    public BookVO postNewAuthor(@RequestBody BookVO book){
        return _bookService.post(book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation("Update book (entire object).")
    public BookVO putAuthor(@PathVariable String id, @RequestBody BookVO book){
        return _bookService.update(id, book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ApiOperation("Update book (partial object).")
    public BookVO patchAuthor(@PathVariable String id, @RequestBody BookVO book){
        //TODO: Write a new method to partial update author object.
        return _bookService.update(id, book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete book.")
    public void deleteBook(@PathVariable String id){
        _bookService.delete(id);
    }

}
