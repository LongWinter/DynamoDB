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
//
//    for (int i = 0; i < 10; i++) {
//      orderService.save(Order.createOrder(Instant.now()));
//    }

//    orderService.delete("112233");

    // Order{orderId='112233', customerId='332211', createdAt=2021-04-04T22:19:55Z, details='this is an test order with details'}

  }
}
