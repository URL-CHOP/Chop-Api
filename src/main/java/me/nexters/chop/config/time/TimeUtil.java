package me.nexters.chop.config.time;

import com.google.protobuf.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author junho.park
 */
public class TimeUtil {
    private static final SimpleDateFormat simpleDateFormat
            = new SimpleDateFormat("yyyy-MM-dd");

    public static String convertTimestampToString(Timestamp timestamp) {
        Date date = new Date(timestamp.getSeconds());
        return simpleDateFormat.format(date);
    }
}
