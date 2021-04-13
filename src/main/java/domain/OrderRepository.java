package domain;

import java.time.Instant;
import java.util.List;

import com.google.common.collect.Range;

public interface OrderRepository {
  public abstract List<Order> findByCustomerIdAndCreatedAtRange(String customerId, Range<Instant> createdAtRange) throws Exception;

  public abstract void save(Order order);

  public abstract void delete(String orderId);
}
