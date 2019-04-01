package dev.kevinyu.service.restful.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "authors")
public class AuthorDO implements Serializable {

    public AuthorDO(){
        books = new ArrayList<>();
    }

    @Id
    @Field("author_id")
    private String authorId;

    @Field("author_name")
    private String authorName;

    @DBRef
    @Field("books")
    private List<BookDO> books;

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

    public List<BookDO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDO> books) {
        this.books = books;
    }
}
