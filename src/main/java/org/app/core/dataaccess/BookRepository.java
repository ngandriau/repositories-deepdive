package org.app.core.dataaccess;

import org.app.core.domain.Book;
import org.app.core.domain.Customer;
import org.app.core.domain.EmailAddress;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * {@link org.springframework.data.repository.Repository} to access {@link org.app.core.domain.Book} instances.
 *
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Long>
{

    Book findByIsbn(String isbn);

    Book findByTitle(String title);
}
