package org.app.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * Created by ngandriau on 5/4/14.
 */
@Embeddable
public class AmountVT
{

    private String currencyCode;
    private BigDecimal value;

    public AmountVT()
    {
    }

    public AmountVT(String currencyCode, BigDecimal value)
    {
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getValue()
    {
        return value;
    }

    public void setValue(BigDecimal value)
    {
        this.value = value;
    }
}
