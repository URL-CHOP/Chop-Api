package me.nexters.chop.config.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author junho.park
 */
@Configuration
public class GrpcConfig {
    @Bean
    public ManagedChannel setChannel() {
        return ManagedChannelBuilder.forAddress("49.236.136.19", 6565)
                .usePlaintext().build();
    }
}
