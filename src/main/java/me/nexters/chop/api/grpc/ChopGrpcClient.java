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
import lombok.extern.slf4j.Slf4j;
import me.nexters.chop.config.time.TimeUtil;
import me.nexters.chop.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import me.nexters.chop.grpc.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author junho.park
 */
@Slf4j
@Component
public class ChopGrpcClient {
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

        log.info("클라이언트 측에서 클릭 정보 전송");

        urlClickStub.unaryRecordCount(url, new StreamObserver<Success>() {
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
                log.info("Grpc 서버 응답 종료");
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
        }
        catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                  return returnTotalCount();
            }
        } catch (NullPointerException e) {
            log.error("null point exception while getting total count: {}", e.getMessage());
        } catch (Exception e) {
            log.error("exception while getting total count: {}", e.getMessage());
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
                return Lists.newArrayList();
            }
        } catch (NullPointerException e) {
            log.error("null point exception while getting click count: {}", e.getMessage());
            return Lists.newArrayList();
        }

        return clickCounts;
    }

    // TODO
    public List<ClickCount> getMonthlyClickCount(String shortUrl, Date date) {
        return null;
    }
}
