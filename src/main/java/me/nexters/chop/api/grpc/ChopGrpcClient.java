package me.nexters.chop.api.grpc;

import com.google.common.collect.Lists;
import com.google.protobuf.Timestamp;
import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.nexters.chop.config.time.TimeUtil;
import me.nexters.chop.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

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

        Platform platform = null;

        try {
            platform = urlStatsServiceBlockingStub.getPlatformCount(urlStatsRequest);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Platform.newBuilder()
                    .setMobile(0)
                    .setBrowser(0)
                    .build();
            }
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
            refererIterator = urlStatsServiceBlockingStub.getRefererCount(urlStatsRequest);

            while (refererIterator.hasNext()) {
                referers.add(refererIterator.next());
            }

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Lists.newArrayList();
            }
        }

        return referers;
    }

    public TotalCount getTotalCount(String shortenUrl) {
        UrlStatsRequest urlStatsRequest = UrlStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .build();

        TotalCount totalCount = null;

        try {
            totalCount = urlStatsServiceBlockingStub.getTotalCount(urlStatsRequest);
        }
        catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                  return returnTotalCount();
            }
        }
        finally {
            return Optional.ofNullable(totalCount).orElseGet(()->returnTotalCount());
        }

    }

    public TotalCount returnTotalCount() {
        return TotalCount.newBuilder()
                .setTotalCount(0)
                .build();
    }

    public List<ClickCount> getWeeklyClickCount(String shortenUrl, LocalDate date) {
        UrlClickStatsRequest urlClickStatsRequest = UrlClickStatsRequest.newBuilder()
                .setShortUrl(shortenUrl)
                .setDate(TimeUtil.convertLocalDateToString(date))
                .build();

        List<ClickCount> clickCounts = new ArrayList<>();
        Iterator<ClickCount> clickCountIterator;

        try {
            clickCountIterator = urlStatsServiceBlockingStub.getWeeklyClickCount(urlClickStatsRequest);

            while (clickCountIterator.hasNext()) {
                clickCounts.add(clickCountIterator.next());
            }
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                Lists.newArrayList();
            }
        }

        return clickCounts;
    }

    // TODO
    public List<ClickCount> getMonthlyClickCount(String shortUrl, Date date) {
        return null;
    }
}
