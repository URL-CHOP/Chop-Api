package me.nexters.chop.config.schedular;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.chop.api.grpc.ChopGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author junho.park
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChopScheduler {
    private final ChopGrpcClient grpcClient;

    @Scheduled(fixedDelay = 3600000)
    public void check() {
        log.info("scheduled data inserted");
        grpcClient.insertStatsToStatsServer("a", "web", "Browser");
    }
}
