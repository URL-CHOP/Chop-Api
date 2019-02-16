package me.nexters.chop.api.grpc;

import com.google.protobuf.Timestamp;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.util.MutableHandlerRegistry;
import me.nexters.chop.grpc.Success;
import me.nexters.chop.grpc.Url;
import me.nexters.chop.grpc.UrlClickServiceGrpc;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ChopGrpcClientTest {
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private final MutableHandlerRegistry serviceRegistry = new MutableHandlerRegistry();
    private ChopGrpcClient client;

    @BeforeEach
    void setUp() throws IOException {
        String serverName = UUID.randomUUID().toString();
        grpcCleanup.register(InProcessServerBuilder.forName(serverName)
                .fallbackHandlerRegistry(serviceRegistry).directExecutor().build().start());

        client = new ChopGrpcClient(InProcessChannelBuilder.forName(serverName).directExecutor().build());
    }

    @Test
    void insertStatsToStatsServer_IsSuccess() {
        Url url = Url.newBuilder().setShortUrl("a")
                .setClickTime(Timestamp.newBuilder())
                .setPlatform("mobile")
                .setReferer("https://google.com").build();
        Success success = Success.newBuilder().setMessage("save success : a").build();

        final AtomicReference<Url> deliveredUrl = new AtomicReference<>();

        UrlClickServiceGrpc.UrlClickServiceImplBase urlClickServiceImplBase =
                new UrlClickServiceGrpc.UrlClickServiceImplBase() {
                    @Override
                    public void unaryRecordCount(Url request, StreamObserver<Success> responseObserver) {
                        deliveredUrl.set(request);
                        responseObserver.onNext(success);
                        responseObserver.onCompleted();
                    }
                };
        serviceRegistry.addService(urlClickServiceImplBase);
        client.insertStatsToStatsServer("a", "https://google.com", "mobile");

        assertThat(url.getShortUrl()).isEqualTo(deliveredUrl.get().getShortUrl());
        assertThat(url.getPlatform()).isEqualTo(deliveredUrl.get().getPlatform());
        assertThat(url.getReferer()).isEqualTo(deliveredUrl.get().getReferer());
    }
}