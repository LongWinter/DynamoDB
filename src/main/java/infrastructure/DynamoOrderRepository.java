package infrastructure;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.Range;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import infrastructure.dynamo.tables.Order;
import domain.OrderRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamoOrderRepository implements OrderRepository {
  private final DynamoDBMapper dynamoDBMapper;
  private final Gson gson;

  public DynamoOrderRepository() {
    this.dynamoDBMapper = new DynamoDBMapper(new AmazonConfig().getDynamoDBClient());
    this.gson = new GsonBuilder().create();
  }

  @Override
  public List<domain.Order> findByCustomerIdAndCreatedAtRange(String customerId, Range<Instant> createdAtRange) throws Exception {
    DynamoDBQueryExpression<Order> queryExpression = getQueryExpressionForFindingOrders(customerId, createdAtRange);
    PaginatedQueryList<Order> paginatedQueryList = dynamoDBMapper.query(Order.class, queryExpression);

    paginatedQueryList.loadAllResults();

    return paginatedQueryList.stream().map(this::deserializeOrder).collect(Collectors.toList());
  }

  @Override
  public void save(domain.Order domain) {
    Order order = Order.fromDomain(domain);
    dynamoDBMapper.save(order);
  }

  public void delete(String orderId) {
    Order order = new Order();
    order.setOrderId(orderId);
    dynamoDBMapper.delete(order);
  }

  private DynamoDBQueryExpression<Order> getQueryExpressionForFindingOrders(String customerId, Range<Instant> createdAtRange) {
    DynamoDBQueryExpression<Order> queryExpression = new DynamoDBQueryExpression<>();
    queryExpression.setIndexName(Order.GSI_NAME);
    queryExpression.withKeyConditionExpression("customer_id = :customer_id and created_at between :low and :high");
    Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
    eav.put(":customer_id", new AttributeValue().withS(customerId));
    eav.put(":low", new AttributeValue().withN(createdAtRange.lowerEndpoint().toEpochMilli() + ""));
    eav.put(":high", new AttributeValue().withN(createdAtRange.upperEndpoint().toEpochMilli() + ""));
    queryExpression.withExpressionAttributeValues(eav);
    queryExpression.withConsistentRead(false);

    return queryExpression;
  }

  private domain.Order deserializeOrder(Order order) {
    return new domain.Order(order.getOrderId(),
                            order.getCustomerId(),
                            Instant.ofEpochMilli(order.getCreateAt()),
                            order.getDetails());
  }
}
