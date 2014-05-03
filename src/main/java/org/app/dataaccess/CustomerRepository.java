package org.app.dataaccess;

import org.app.domain.Customer;
import org.app.domain.EmailAddress;
import org.springframework.data.repository.Repository;

/**
 * {@link Repository} to access {@link Customer} instances.
 *
 * @author Oliver Gierke
 * @since Step 2
 */
public interface CustomerRepository extends Repository<Customer, Long> {

    /**
     * Returns the {@link Customer} with the given identifier.
     *
     * @param id the id to search for.
     * @since Step 2
     * @return
     */
    Customer findOne(Long id);

    /**
     * Saves the given {@link Customer}.
     *
     * @param customer the {@link Customer} to search for.
     * @since Step 2
     * @return
     */
    Customer save(Customer customer);

    /**
     * Returns the customer with the given {@link EmailAddress}.
     *
     * @param emailAddress the {@link EmailAddress} to search for.
     * @since Step 2
     * @return
     */
    Customer findByEmailAddress(EmailAddress emailAddress);
}
