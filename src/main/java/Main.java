import com.google.common.collect.Range;
import domain.Order;
import domain.OrderService;
import infrastructure.DynamoOrderRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {
    OrderService orderService = new OrderService(new DynamoOrderRepository());
    Instant now = Instant.now();
    Instant prev = now.minus(30, ChronoUnit.DAYS);
    List<domain.Order> orders = orderService.findOrderByCustomerAndCreatedRange("332211", Range.closed(prev, now));
    for (Order order : orders) {
      System.out.println(order);
    }
  }
}
