package fun.baozi.boot.helper;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author baozi
 * 2019-07-26
 */
@Component
public class DateHelper {

    /**
     * 将时间转我字符串
     * @param date 时间
     * @return yyyy-MM-dd-HH-mm-ss-ms
     */
    public String getCurrentDateStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second + "-" + millisecond;
    }

}
