package domain;

import java.time.Instant;

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
}
