package me.nexters.chop.config.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author junho.park
 */
@Configuration
public class GrpcConfig {
    @Bean
    public ManagedChannel setChannel() {
        return ManagedChannelBuilder.forAddress("49.236.136.197", 6565)
                .usePlaintext()
                .keepAliveTime(5, TimeUnit.MINUTES)
                .build();
    }
}
