package dev.kevinyu.service.restful.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "authors")
public class AuthorDO implements Serializable {

    public AuthorDO(){
        bookIds = new ArrayList<>();
    }

    @Id
    @Field("_id")
    private ObjectId authorId;

    @Field("author_name")
    private String authorName;

    @Field("book_ids")
    private List<ObjectId> bookIds;

    public ObjectId getAuthorId() {
        return authorId;
    }

    public void setAuthorId(ObjectId authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<ObjectId> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<ObjectId> bookIds) {
        this.bookIds = bookIds;
    }
}
