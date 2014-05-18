package org.app.rest.domain;

import java.math.BigDecimal;

/**
 * Created by ngandriau on 5/18/14.
 */
public class BookR
{
    private Long id;
    private String title;
    private String isbn;
    private BigDecimal price;

    public BookR()
    {
    }

    public BookR(Long id, String isbn, String title)
    {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    @Override
    public String toString()
    {
        return "BookR{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BookR)) return false;

        BookR bookR = (BookR) o;

        if (!id.equals(bookR.id)) return false;
        if (!isbn.equals(bookR.isbn)) return false;
        if (!title.equals(bookR.title)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + isbn.hashCode();
        return result;
    }
}
