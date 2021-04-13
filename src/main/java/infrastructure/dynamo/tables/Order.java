package infrastructure.dynamo.tables;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "orders")
public class Order {
  public final static String GSI_NAME = "customer_id-created_at-index-copy";

  @DynamoDBHashKey(attributeName = "order_id")
  private String orderId;

  @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_NAME, attributeName = "customer_id")
  private String customerId;

  @DynamoDBIndexRangeKey(globalSecondaryIndexName = GSI_NAME, attributeName = "created_at")
  private Long createAt;

  @DynamoDBAttribute(attributeName = "details")
  private String details;

  public Order() {
  }

  public Order(String orderId, String customerId, Long createAt, String details) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.createAt = createAt;
    this.details = details;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Long createAt) {
    this.createAt = createAt;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return "Order{" +
            "orderId='" + orderId + '\'' +
            ", customerId='" + customerId + '\'' +
            ", createAt=" + createAt +
            ", details='" + details + '\'' +
            '}';
  }

  public static Order fromDomain(domain.Order order) {
    return new Order(order.getOrderId(),
                     order.getCustomerId(),
                     order.getCreatedAt().toEpochMilli(),
                     order.getDetails());
  }
}
