package org.app;

import org.app.dataaccess.CustomerRepository;
import org.app.domain.Customer;
import org.app.domain.EmailAddress;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Integration tests for {@link CustomerRepository}.
 *
 * @author Oliver Gierke
 * @since Step 2
 */
public class CustomerRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    CustomerRepository repository;

    /**
     * @since Step 2.1
     */
    @Test
    public void findsCustomerById() {

        Customer customer = repository.findOne(1L);

        assertThat(customer, is(notNullValue()));
        assertThat(customer.getFirstname(), is("Dave"));
        assertThat(customer.getLastname(), is("Matthews"));
    }

    /**
     * @since Step 2.2
     */
    @Test
    public void savesNewCustomer() {

        Customer stefan = new Customer("Stefan", "Lassard");
        Customer result = repository.save(stefan);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getFirstname(), is("Stefan"));
        assertThat(result.getLastname(), is("Lassard"));
    }

    /**
     * @since Step 2.2
     */
    @Test
    public void savesExistingCustomer() {

        Customer dave = repository.findOne(1L);
        dave.setEmailAddress(new EmailAddress("davematthews@dmband.com"));
        repository.save(dave);

        Customer result = repository.findOne(1L);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getFirstname(), is("Dave"));
        assertThat(result.getEmailAddress(), is(new EmailAddress("davematthews@dmband.com")));
    }
}
