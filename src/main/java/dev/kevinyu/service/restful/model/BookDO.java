package dev.kevinyu.service.restful.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "books")
public class BookDO implements Serializable {

    public BookDO(){
        authorIds = new ArrayList<>();
    }

    @Id
    @Field("_id")
    private ObjectId bookId;

    @Field("book_name")
    private String bookName;

    @Field("isbn")
    private String isbn;

    @Field("unit_price")
    private double price;

    @Field("author_ids")
    private List<ObjectId> authorIds;

    public ObjectId getBookId() {
        return bookId;
    }

    public void setBookId(ObjectId bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ObjectId> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<ObjectId> authorIds) {
        this.authorIds = authorIds;
    }
}
