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
        return ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext().build();
    }
}
