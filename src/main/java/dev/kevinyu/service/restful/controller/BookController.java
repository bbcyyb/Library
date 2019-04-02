package dev.kevinyu.service.restful.controller;

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
    public List<BookVO> getAuthorList(){
        return _bookService.getList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Retrieve an entire book object.")
    public BookVO getAuthor(@PathVariable String id){
        return _bookService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create a new book.")
    public BookVO postNewAuthor(@RequestBody BookVO book){
        return _bookService.post(book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation("Update a book (entire object).")
    public BookVO putAuthor(@PathVariable String id, @RequestBody BookVO book){
        return _bookService.update(id, book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ApiOperation("Update a book (partial object).")
    public BookVO PATCHAuthor(@PathVariable String id, @RequestBody BookVO book){
        //TODO: Write a new method to partial update author object.
        return _bookService.update(id, book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update a book (entire object).")
    public void putAuthor(@PathVariable String id){
        _bookService.delete(id);
    }

}
