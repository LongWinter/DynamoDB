package domain;

import com.google.common.collect.Range;

import java.time.Instant;
import java.util.List;

public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<Order> findOrderByCustomerAndCreatedRange(String customerId, Range<Instant> createdAtRange) throws Exception{
    return orderRepository.findByCustomerIdAndCreatedAtRange(customerId, createdAtRange);
  }

  public void save(String orderId, String customerId, String details) {

  }
}
