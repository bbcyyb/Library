package dev.kevinyu.service.restful.controller;

import dev.kevinyu.service.restful.model.AuthorVO;
import dev.kevinyu.service.restful.model.BookVO;
import dev.kevinyu.service.restful.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@Api("AuthorController")
public class AuthorController {

    private AuthorService _authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        _authorService = authorService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation("List all authors.")
    public List<AuthorVO> getAuthorList(@RequestParam(value="embed",required = false, defaultValue="false") boolean embed,
                                        @RequestParam(value="sortby", required = false, defaultValue="") String sortby,
                                        @RequestParam(value="offset", required = false, defaultValue="0") int offset,
                                        @RequestParam(value="limit", required = false, defaultValue="0") int limit,
                                        @RequestParam(value="authorName", required = false, defaultValue="") String authorName){
        return _authorService.getList(embed, sortby, offset, limit, authorName);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Retrieve an entire author object.")
    public AuthorVO getAuthors(@PathVariable String id, @RequestParam(value="embed",required = false, defaultValue="false")boolean embed){
        return _authorService.getById(id, embed);
    }

    @RequestMapping(value = "/{id}/books", method = RequestMethod.GET)
    @ApiOperation("Retrieve book list of author.")
    public List<BookVO> getBooksByAuthorId(@PathVariable String id){
        AuthorVO authorVO = _authorService.getById(id, true);
        return authorVO.getBooks();
    }

    @RequestMapping(value = "/{id}/books", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add an author to a specific book author list.")
    public AuthorVO addBookToAuthor(@PathVariable String id, @RequestBody BookVO book) {
        return _authorService.addBookToAuthor(id, book);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add a new author.")
    public AuthorVO createAuthor(@RequestBody AuthorVO author){
        return _authorService.createAuthor(author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation("Update a book (entire object).")
    public AuthorVO updateAuthor(@PathVariable String id, @RequestBody AuthorVO author){
        return _authorService.updateAuthor(id, author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ApiOperation("Update a book (partial object).")
    public AuthorVO updatePartialAuthor(@PathVariable String id, @RequestBody AuthorVO author){
        //TODO: Write a new method to partial update author object.
        return _authorService.updateAuthor(id, author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete author.")
    public void deleteAuthor(@PathVariable String id){
        _authorService.deleteAuthor(id);
    }

    @RequestMapping(value = "/{authorId}/books/{bookId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Remove author from a specific book author list.")
    public void removeAuthorFromBook(@PathVariable String bookId, @PathVariable String authorId){
        _authorService.removeBookFromAuthor(bookId, authorId);
    }
}
