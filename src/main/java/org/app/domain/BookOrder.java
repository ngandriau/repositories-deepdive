package org.app.domain;

import javax.persistence.*;
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

    @Override
    public String toString()
    {
        return "Order{" +
                "customer=" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BookOrder)) return false;
        if (!super.equals(o)) return false;

        BookOrder order = (BookOrder) o;

        if (!customer.equals(order.customer)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + customer.hashCode();
        return result;
    }
}
