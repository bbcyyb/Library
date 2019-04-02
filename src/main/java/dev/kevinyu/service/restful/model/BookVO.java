package dev.kevinyu.service.restful.model;

import java.util.ArrayList;
import java.util.List;

public class BookVO {

    public BookVO(){
        authors = new ArrayList<>();
    }

    private String bookId;

    private String bookName;

    private String isbn;

    private double price;

    private List<AuthorVO> authors;

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

    public List<AuthorVO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorVO> authors) {
        this.authors = authors;
    }
}
