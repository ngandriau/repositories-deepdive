package org.app.rest.domain;

import java.util.Date;
import java.util.List;

public class OrderR
{
    private Date dateTimeOfSubmission;

    private List<String> isbns;

    private Long id;

    public Date getDateTimeOfSubmission()
    {
        return dateTimeOfSubmission;
    }

    public void setDateTimeOfSubmission(Date dateTimeOfSubmission)
    {
        this.dateTimeOfSubmission = dateTimeOfSubmission;
    }

    public List<String> getIsbns()
    {
        return isbns;
    }

    public void setIsbns(List<String> isbns)
    {
        this.isbns = isbns;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof OrderR)) return false;

        OrderR orderR = (OrderR) o;

        if (!dateTimeOfSubmission.equals(orderR.dateTimeOfSubmission)) return false;
        if (!id.equals(orderR.id)) return false;
        if (isbns != null ? !isbns.equals(orderR.isbns) : orderR.isbns != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = dateTimeOfSubmission.hashCode();
        result = 31 * result + (isbns != null ? isbns.hashCode() : 0);
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "OrderR{" +
                "dateTimeOfSubmission=" + dateTimeOfSubmission +
                ", isbns=" + isbns +
                ", id=" + id +
                '}';
    }
}
