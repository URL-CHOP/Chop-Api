package me.nexters.chop.api.grpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.protobuf.Timestamp;
import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import me.nexters.chop.grpc.ClickCount;
import me.nexters.chop.grpc.Platform;
import me.nexters.chop.grpc.Referer;
import me.nexters.chop.grpc.Success;
import me.nexters.chop.grpc.TotalCount;
import me.nexters.chop.grpc.Url;
import me.nexters.chop.grpc.UrlClickServiceGrpc;
import me.nexters.chop.grpc.UrlClickStatsRequest;
import me.nexters.chop.grpc.UrlStatsRequest;
import me.nexters.chop.grpc.UrlStatsServiceGrpc;

/**
 * @author junho.park
 */
@Slf4j
@Component
public class ChopGrpcClient {
    private static Logger logger = LoggerFactory.getLogger(ChopGrpcClient.class);

    private UrlClickServiceGrpc.UrlClickServiceStub urlClickStub;
    private UrlStatsServiceGrpc.UrlStatsServiceBlockingStub urlStatsServiceBlockingStub;

    public ChopGrpcClient(Channel channel) {
        urlStatsServiceBlockingStub = UrlStatsServiceGrpc.newBlockingStub(channel);
        urlClickStub = UrlClickServiceGrpc.newStub(channel);
    }

    public void insertStatsToStatsServer(String shortenUrl, String referer, String userAgent) {
        Url url = Url.newBuilder().setShortUrl(shortenUrl)
                .setClickTime(generateCurrentTimestamp())
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
                logger.info("Grpc 서버 응답 종료");
            }
        });
    }

    private Timestamp generateCurrentTimestamp() {
        long currentTimeMillis = System.currentTimeMillis();
        return Timestamp.newBuilder().
            setSeconds(currentTimeMillis / 1000)
            .setNanos((int) ((currentTimeMillis % 1000) * 1000000)).build();
    }

    public Platform getPlatformStats(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        Platform platform = Platform.newBuilder().setBrowser(0).setMobile(0).build();

        try {
            platform = urlStatsServiceBlockingStub.getPlatformCount(urlStatsRequest);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                log.error("url not found from gRPC while getting platform count : {}", e.getMessage());
            }
        } catch (NullPointerException e) {
            log.error("null point exception while getting platform count: {}", e.getMessage());
        } catch (Exception e) {
            log.error("exception while getting platform count: {}", e.getMessage());
        } finally {
            return platform;
        }
    }

    public List<Referer> getRefererStats(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        List<Referer> referers = new ArrayList<>();
        Iterator<Referer> refererIterator;

        try {
            refererIterator = urlStatsServiceBlockingStub.getRefererCount(urlStatsRequest);

            while (refererIterator.hasNext()) {
                referers.add(refererIterator.next());
            }

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Lists.newArrayList();
            }
        } catch (NullPointerException e) {
            log.error("null point exception while getting referer count: {}", e.getMessage());
            return Lists.newArrayList();
        }

        return referers;
    }

    public TotalCount getTotalCount(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        TotalCount totalCount = TotalCount.newBuilder().setTotalCount(0).build();

        try {
            totalCount = urlStatsServiceBlockingStub.getTotalCount(urlStatsRequest);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                log.error("url not found from gRPC while getting total count : {}", e.getMessage());
            }
        } catch (NullPointerException e) {
            log.error("null point exception while getting total count: {}", e.getMessage());
        } catch (Exception e) {
            log.error("exception while getting total count: {}", e.getMessage());
        } finally {
            return totalCount;
        }
    }

    public List<ClickCount> getClickCount(String shortenUrl, int week) {
        UrlClickStatsRequest urlClickStatsRequest = UrlClickStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .setWeek(week)
                .build();

        List<ClickCount> clickCounts = new ArrayList<>();
        Iterator<ClickCount> clickCountIterator;

        try {
            clickCountIterator = urlStatsServiceBlockingStub.getClickCount(urlClickStatsRequest);

            while (clickCountIterator.hasNext()) {
                clickCounts.add(clickCountIterator.next());
            }
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Lists.newArrayList();
            }
        } catch (NullPointerException e) {
            log.error("null point exception while getting click count: {}", e.getMessage());
            return Lists.newArrayList();
        }

        return clickCounts;
    }
}
