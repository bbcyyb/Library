package dev.kevinyu.service.restful.model;

import java.util.ArrayList;
import java.util.List;

public class AuthorVO {

    public AuthorVO(){
        books = new ArrayList<>();
    }

    private String authorId;

    private String authorName;

    private List<BookVO> books;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }
}
