package me.nexters.chop.config.grpc;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author junho.park
 */
@Configuration
public class GrpcConfig {
    @Bean
    public ManagedChannel setChannel() {
        return NettyChannelBuilder.forAddress("49.236.136.197", 6565)
                .usePlaintext().build();
    }
}
