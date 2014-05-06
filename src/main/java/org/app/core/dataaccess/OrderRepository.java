package org.app.core.dataaccess;

import org.app.core.domain.BookOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * {@link org.springframework.data.repository.Repository} to access {@link org.app.core.domain.BookOrder} instances.
 *
 */
public interface OrderRepository extends PagingAndSortingRepository<BookOrder, Long>
{


}
