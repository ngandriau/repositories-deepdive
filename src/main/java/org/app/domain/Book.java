package org.app.domain;

import javax.persistence.Entity;

@Entity
public class Book extends AbstractEntity {

    private String isbn, title, author;

    public Book()
    {
    }

    public Book(String isbn, String title, String author)
    {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (!isbn.equals(book.isbn)) return false;
        if (!title.equals(book.title)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + isbn.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
