package org.app.rest.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderR
{
    private Long id;
    private String status;

    private Date dateTimeOfSubmission;

    private String customerFirstname;
    private String customerLastname;

    private BigDecimal amount;

    private List<BookR> books;

    private boolean needConfirmation = false;

    public Date getDateTimeOfSubmission()
    {
        return dateTimeOfSubmission;
    }

    public void setDateTimeOfSubmission(Date dateTimeOfSubmission)
    {
        this.dateTimeOfSubmission = dateTimeOfSubmission;
    }

    public List<BookR> getBooks()
    {
        return books;
    }

    public void setBooks(List<BookR> books)
    {
        this.books = books;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public OrderR()
    {
    }

    public OrderR(Date dateTimeOfSubmission, Long id)
    {
        this.dateTimeOfSubmission = dateTimeOfSubmission;
        this.id = id;
    }

    public String getCustomerFirstname()
    {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname)
    {
        this.customerFirstname = customerFirstname;
    }

    public String getCustomerLastname()
    {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname)
    {
        this.customerLastname = customerLastname;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public boolean isNeedConfirmation()
    {
        return needConfirmation;
    }

    public void setNeedConfirmation(boolean needConfirmation)
    {
        this.needConfirmation = needConfirmation;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof OrderR)) return false;

        OrderR orderR = (OrderR) o;

        if (!dateTimeOfSubmission.equals(orderR.dateTimeOfSubmission)) return false;
        if (!id.equals(orderR.id)) return false;
        if (books != null ? !books.equals(orderR.books) : orderR.books != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = dateTimeOfSubmission.hashCode();
        result = 31 * result + (books != null ? books.hashCode() : 0);
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "OrderR{" +
                "dateTimeOfSubmission=" + dateTimeOfSubmission +
                ", books=" + books +
                ", id=" + id +
                '}';
    }
}
