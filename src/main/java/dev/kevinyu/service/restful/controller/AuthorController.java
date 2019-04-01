package dev.kevinyu.service.restful.controller;

import dev.kevinyu.service.restful.model.AuthorDO;
import dev.kevinyu.service.restful.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<AuthorDO> getAuthorList(){
        return _authorService.getList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Retrieve an entire author object.")
    public AuthorDO getAuthor(@PathVariable String id){
        return _authorService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create a new author.")
    public AuthorDO postNewAuthor(@RequestBody AuthorDO author){
        return _authorService.post(author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation("Update a book (entire object).")
    public AuthorDO putAuthor(@PathVariable String id, @RequestBody AuthorDO author){
        return _authorService.update(id, author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ApiOperation("Update a book (partial object).")
    public AuthorDO PATCHAuthor(@PathVariable String id, @RequestBody AuthorDO author){
        //TODO: Write a new method to partial update author object.
        return _authorService.update(id, author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update a book (entire object).")
    public void putAuthor(@PathVariable String id){
        _authorService.delete(id);
    }
}
