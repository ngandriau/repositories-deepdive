package org.app;

import org.app.dataaccess.CustomerRepository;
import org.app.domain.Customer;
import org.app.domain.EmailAddress;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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
     * @since Step 2.3
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

    /**
     * @since Step 2.4
     */
    @Test
    public void findsCustomersByEmailAddress() {

        Customer result = repository.findByEmailAddress(new EmailAddress("dave@dmband.com"));

        assertThat(result, is(notNullValue()));
        assertThat(result.getFirstname(), is("Dave"));
        assertThat(result.getLastname(), is("Matthews"));
    }

    /**
     * @since Step 3.1
     */
    @Test
    public void findsAllCustomers() {

        Iterable<Customer> customers = repository.findAll();
        assertThat(customers, is(Matchers.<Customer> iterableWithSize(3)));
    }

    /**
     * @since Step 3.2
     */
    @Test
    public void deletesCustomer() {

        repository.delete(1L);

        assertThat(repository.findOne(1L), is(nullValue()));
    }

    /**
     * @since Step 4.1
     */
    @Test
    public void accessesCustomersPageByPage() {

        Page<Customer> result = repository.findAll(new PageRequest(1, 1));

        assertThat(result, is(notNullValue()));
        assertThat(result.isFirstPage(), is(false));
        assertThat(result.isLastPage(), is(false));
        assertThat(result.getNumberOfElements(), is(1));
    }
}
