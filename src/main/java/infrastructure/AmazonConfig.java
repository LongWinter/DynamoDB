package infrastructure;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class AmazonConfig {

  private static final Regions REGION = Regions.US_EAST_2;
  private static final String PROFILE_NAME = "default";
  private static AmazonDynamoDB amazonDynamoDB = null;

  public AmazonDynamoDB getDynamoDBClient() {
    if (amazonDynamoDB == null) {
      amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                                                  .withRegion(REGION)
                                                  .withCredentials(new ProfileCredentialsProvider(PROFILE_NAME))
                                                  .build();
    }
    return amazonDynamoDB;
  }
}
