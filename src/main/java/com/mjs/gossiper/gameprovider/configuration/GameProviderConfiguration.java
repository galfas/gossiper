package com.mjs.gossiper.gameprovider.configuration;

import com.mjs.gossiper.gameprovider.contract.RiotProvider;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration("gameAppConfig")
public class GameProviderConfiguration {

    @Value("${riot.api.host}")
    private String apiEndpoint;

    @Value("${riot.api.timeout}")
    private static int TIME_OUT;


    private static OkHttpClient configureClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        builder.connectionPool(new ConnectionPool(3, 5, TimeUnit.MINUTES));

        return builder.build();
    }

    @Bean(name = "riotHttpClient")
    public RiotProvider buildStatsProvider() {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .client(new feign.okhttp.OkHttpClient(configureClient()))
                .target(RiotProvider.class, apiEndpoint);
    }
}
