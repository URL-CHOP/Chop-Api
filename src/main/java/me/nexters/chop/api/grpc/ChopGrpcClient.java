package me.nexters.chop.api.grpc;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import me.nexters.chop.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author junho.park
 */
@Component
public class ChopGrpcClient {
    public static Logger logger = LoggerFactory.getLogger(ChopGrpcClient.class);

    private UrlClickServiceGrpc.UrlClickServiceStub urlClickStub;
    private UrlStatsServiceGrpc.UrlStatsServiceBlockingStub urlStatsServiceBlockingStub;

    public ChopGrpcClient(Channel channel) {
        urlStatsServiceBlockingStub = UrlStatsServiceGrpc.newBlockingStub(channel);
        urlClickStub = UrlClickServiceGrpc.newStub(channel);
    }

    public void insertStatsToStatsServer(String shortenUrl, String referer, String userAgent) {
        Url url = Url.newBuilder().setShortUrl(shortenUrl)
                .setClickTime(System.currentTimeMillis())
                .setPlatform(userAgent)
                .setReferer(referer).build();

        logger.info("클라이언트 측에서 클릭 정보 전송");

        urlClickStub.unaryRecordCount(url, new StreamObserver<Success>() {
            @Override
            public void onNext(Success success) {
                logger.info(success.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("서버 응답 종료");
            }
        });
    }

    public Platform getPlatformStats(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        Platform platform = urlStatsServiceBlockingStub.getPlatformCount(urlStatsRequest);

        logger.info("Server response [browser] {} ", platform.getBrowser());
        logger.info("Server response [mobile] {} ", platform.getMobile());

        return platform;
    }

    public Referer getRefererStats(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        Referer referer = urlStatsServiceBlockingStub.getRefererCount(urlStatsRequest);

        logger.info("Server response [referer] {} ", referer.getRefererList());
        logger.info("Server response [referer count] {} ", referer.getCountList());

        return referer;
    }

    public TotalCount getTotalCount(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        TotalCount totalCount = urlStatsServiceBlockingStub.getTotalCount(urlStatsRequest);

        logger.info("Server response [total count] {} ", totalCount.getTotalCount());

        return totalCount;
    }
}
