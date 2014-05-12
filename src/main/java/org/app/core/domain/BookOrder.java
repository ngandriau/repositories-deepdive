package org.app.core.domain;

import org.app.api.OrderStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BookOrder extends AbstractEntity
{


    @ManyToOne(optional = false)
    private Customer customer;

    /**
     * An order has not many books, and we will need them most ofthe time, so load them with the order
     */
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Book> orderedBooks = new HashSet<>();

    @Temporal (TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date creationDate = new Date();

    @Temporal (TemporalType.TIME)
    @Column(updatable = false)
    private Date creationTime = new Date();

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.initial;


    protected BookOrder(){}

    public BookOrder(Customer customer)
    {
        this.customer = customer;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Set<Book> getOrderedBooks()
    {
        return orderedBooks;
    }

    public void setOrderedBooks(Set<Book> orderedBooks)
    {
        this.orderedBooks = orderedBooks;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public OrderStatus getStatus()
    {
        return status;
    }

    public void setStatus(OrderStatus status)
    {
        this.status = status;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BookOrder)) return false;
        if (!super.equals(o)) return false;

        BookOrder bookOrder = (BookOrder) o;

        if (!creationDate.equals(bookOrder.creationDate)) return false;
        if (!customer.equals(bookOrder.customer)) return false;
        if (orderedBooks != null ? !orderedBooks.equals(bookOrder.orderedBooks) : bookOrder.orderedBooks != null)
            return false;
        if (status != bookOrder.status) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + (orderedBooks != null ? orderedBooks.hashCode() : 0);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }
}
