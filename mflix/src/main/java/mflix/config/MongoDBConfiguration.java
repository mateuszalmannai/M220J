package mflix.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Configuration
@Service
public class MongoDBConfiguration {

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public MongoClient mongoClient(@Value("${spring.mongodb.uri}") String connectionString) {

    ConnectionString connString = new ConnectionString(connectionString);

    //DONE> Ticket: Handling Timeouts - configure the expected
    // WriteConcern `wtimeout` and `connectTimeoutMS` values
    WriteConcern writeConcern = WriteConcern.MAJORITY.withWTimeout(2500, TimeUnit.MILLISECONDS);
    MongoClientSettings settings = MongoClientSettings
            .builder()
            .writeConcern(writeConcern)
            .applyConnectionString(connString)
            .build();
    MongoClient mongoClient = MongoClients.create(settings);

    return mongoClient;
  }
}
