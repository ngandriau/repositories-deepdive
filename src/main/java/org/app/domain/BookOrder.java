package org.app.domain;

import javax.persistence.*;

@Entity
public class BookOrder extends AbstractEntity
{


    @ManyToOne(optional = false)
    private Customer customer;


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
