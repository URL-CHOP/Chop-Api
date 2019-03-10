package me.nexters.chop.api.grpc;


import com.google.common.collect.Lists;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.nexters.chop.grpc.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author junho.park
 */
@Slf4j
@Component
public class ChopGrpcClient {
    private UrlClickServiceGrpc.UrlClickServiceStub urlClickStub;
    private UrlStatsServiceGrpc.UrlStatsServiceBlockingStub urlStatsServiceBlockingStub;

    public ChopGrpcClient(ManagedChannel channel) {
        urlStatsServiceBlockingStub = UrlStatsServiceGrpc.newBlockingStub(channel);
        urlClickStub = UrlClickServiceGrpc.newStub(channel);
    }

    public void insertStatsToStatsServer(String shortenUrl, String referer, String userAgent) {
        Url url = Url.newBuilder().setShortUrl(shortenUrl)
                .setClickTime(generateCurrentTimestamp())
                .setPlatform(userAgent)
                .setReferer(referer).build();

        log.info("클라이언트 측에서 " + shortenUrl + " 클릭 정보 전송");

        urlClickStub.withDeadlineAfter(800, TimeUnit.MILLISECONDS).unaryRecordCount(url, new StreamObserver<Success>() {
            @Override
            public void onNext(Success success) {
                log.info(success.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("url : " + shortenUrl + " gRPC 서버 응답 종료");
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
            platform = urlStatsServiceBlockingStub.withDeadlineAfter(800, TimeUnit.MILLISECONDS).getPlatformCount(urlStatsRequest);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                log.error("url : " + shortenUrl + " url not found from gRPC while getting platform count : {}", e.getMessage());
            }
            if (e.getStatus().getCode() == Status.Code.CANCELLED) {
                log.error("url : " + shortenUrl + " cancelled by client: {}", e.getMessage());
            }
        } catch (NullPointerException e) {
            log.error("url : " + shortenUrl + " null point exception while getting platform count: {}", e.getMessage());
        } catch (Exception e) {
            log.error("url : " + shortenUrl + " exception while getting platform count: {}", e.getMessage());
        }

        return platform;
    }

    public List<Referer> getRefererStats(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        List<Referer> referers = new ArrayList<>();
        Iterator<Referer> refererIterator;

        try {
            refererIterator = urlStatsServiceBlockingStub.withDeadlineAfter(800, TimeUnit.MILLISECONDS).getRefererCount(urlStatsRequest);

            while (refererIterator.hasNext()) {
                referers.add(refererIterator.next());
            }

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Lists.newArrayList();
            }
            if (e.getStatus().getCode() == Status.Code.CANCELLED) {
                log.error("url : " + shortenUrl + " cancelled by client: {}", e.getMessage());
            }
        } catch (NullPointerException e) {
            log.error("url : " + shortenUrl + " null point exception while getting referer count: {}", e.getMessage());
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
            totalCount = urlStatsServiceBlockingStub.withDeadlineAfter(800, TimeUnit.MILLISECONDS).getTotalCount(urlStatsRequest);
        }
        catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                  return totalCount;
            }
            if (e.getStatus().getCode() == Status.Code.CANCELLED) {
                log.error("url : " + shortenUrl + " cancelled by client: {}", e.getMessage());
            }
        } catch (NullPointerException e) {
            log.error("url : " + shortenUrl + " null point exception while getting total count: {}", e.getMessage());
        } catch (Exception e) {
            log.error("url : " + shortenUrl + " exception while getting total count: {}", e.getMessage());
        }

        return totalCount;
    }

    public List<ClickCount> getClickCount(String shortenUrl, int week) {
        UrlClickStatsRequest urlClickStatsRequest = UrlClickStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .setWeek(week)
                .build();

        List<ClickCount> clickCounts = new ArrayList<>();
        Iterator<ClickCount> clickCountIterator;

        try {
            clickCountIterator = urlStatsServiceBlockingStub.withDeadlineAfter(800, TimeUnit.MILLISECONDS).getClickCount(urlClickStatsRequest);

            while (clickCountIterator.hasNext()) {
                clickCounts.add(clickCountIterator.next());
            }
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Lists.newArrayList();
            }
            if (e.getStatus().getCode() == Status.Code.CANCELLED) {
                log.error("url : " + shortenUrl + " cancelled by client: {}", e.getMessage());
            }
        } catch (NullPointerException e) {
            log.error("url : " + shortenUrl + " null point exception while getting click count: {}", e.getMessage());
            return Lists.newArrayList();
        }

        return clickCounts;
    }
}
