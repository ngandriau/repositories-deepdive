package org.app.dataaccess;

import org.app.domain.BookOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * {@link org.springframework.data.repository.Repository} to access {@link org.app.domain.BookOrder} instances.
 *
 */
public interface OrderRepository extends PagingAndSortingRepository<BookOrder, Long>
{


}
