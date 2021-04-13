package domain;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Order {
  private String orderId;
  private String customerId;
  private Instant createdAt;
  private String details;

  public Order(String orderId, String customerId, Instant createdAt, String details) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.createdAt = createdAt;
    this.details = details;
  }

  @Override
  public String toString() {
    return "Order{" +
            "orderId='" + orderId + '\'' +
            ", customerId='" + customerId + '\'' +
            ", createdAt=" + createdAt +
            ", details='" + details + '\'' +
            '}';
  }

  public static Order createOrder(Instant now) {
    return new Order(
            UUID.randomUUID().toString(),
            "332211",
            now,
            UUID.randomUUID().toString()
    );
  }
}
