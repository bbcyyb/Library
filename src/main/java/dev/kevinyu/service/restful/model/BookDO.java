package dev.kevinyu.service.restful.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "books")
public class BookDO {

    @Id
    @Field("book_id")
    private String bookId;

    @Field("book_name")
    private String bookName;

    @Field("isbn")
    private String isbn;

    @Field("unit_price")
    private double price;

    @Field("authors")
    private List<AuthorDO> authors;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
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

    public List<AuthorDO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDO> authors) {
        this.authors = authors;
    }
}
