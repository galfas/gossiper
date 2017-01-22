package com.mjs.gossiper.dao.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("mongoAppConfig")
public class MongoApp {

    @Value("${database.mongodb.name}")
    private String databaseName;

    @Value("${database.mongodb.host}")
    private String databaseHost;

    @Value("${database.mongodb.port}")
    private Integer databasePort;

    @Value("${database.mongodb.pool.size}")
    private Integer databasePoolSize;

    @Value("${database.mongodb.connection.timeout}")
    private Integer databaseConTimeout;

    @Value("${database.mongodb.keep.alive}")
    private boolean databaseKeepAlive;

    @Value("${database.mongodb.max.idle.timeout}")
    private Integer maxIdleTimeout;

    @Bean(name = "mongoDatabase")
    public MongoDatabase getMongoDatabase() {

        MongoClientURI uri = new MongoClientURI("mongodb://"+databaseHost+":"+databasePort+"/?maxPoolSize="+databasePoolSize,
                MongoClientOptions.builder()
                        .connectTimeout(databaseConTimeout)
                        .socketKeepAlive(databaseKeepAlive)
                        .maxConnectionIdleTime(maxIdleTimeout));

        MongoClient client = new MongoClient(uri);

        return client.getDatabase(databaseName);
    }
}