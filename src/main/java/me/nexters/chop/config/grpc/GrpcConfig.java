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
    private static final String STAT_SERVER_IP = "45.119.144.56";
    private static final int STAT_SERVER_GRPC_PORT = 6565;

    @Bean
    public ManagedChannel setChannel() {
        return ManagedChannelBuilder.forAddress(STAT_SERVER_IP, STAT_SERVER_GRPC_PORT)
                .usePlaintext()
                .keepAliveTime(5, TimeUnit.MINUTES)
                .build();
    }
}
