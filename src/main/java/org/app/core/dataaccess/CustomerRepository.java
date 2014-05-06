package org.app.core.dataaccess;

import org.app.core.domain.Customer;
import org.app.core.domain.EmailAddress;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

/**
 * {@link Repository} to access {@link Customer} instances.
 *
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>
{

    /**
     * Returns the customer with the given {@link EmailAddress}.
     *
     * @param emailAddress the {@link EmailAddress} to search for.
     * @return
     */
    Customer findByEmailAddress(EmailAddress emailAddress);

    Customer findByLastname(String lastname);
}
