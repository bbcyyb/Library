package dev.kevinyu.service.restful.controller.activity;

import dev.kevinyu.service.restful.exception.NotFoundException;
import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.service.AuthorService;
import dev.kevinyu.service.restful.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/counts")
@Api("CountController")
public class CountController {

    private AuthorService authorService;
    private BookService bookService;

    @Autowired
    public CountController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    @ApiOperation("Count the author documents.")
    public long getAuthorsCount(@RequestParam(value="authorName", required = false, defaultValue="") String authorName) {
        return authorService.count(authorName);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ApiOperation("Count the book documents.")
    public long getBooksCount(@RequestParam(value="bookName", required = false, defaultValue="") String bookName,
                              @RequestParam(value="isbn", required = false, defaultValue="") String isbn) {
        return bookService.count(bookName, isbn);
    }
}
