package org.app.dataaccess;

import org.app.domain.Book;
import org.app.domain.Customer;
import org.app.domain.EmailAddress;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * {@link org.springframework.data.repository.Repository} to access {@link org.app.domain.Book} instances.
 *
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Long>
{

    Customer findByIsbn(String isbn);

    Customer findByTitle(String title);
}
