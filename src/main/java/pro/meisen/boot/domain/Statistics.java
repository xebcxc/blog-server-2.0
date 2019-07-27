package pro.meisen.boot.domain;

import pro.meisen.boot.domain.common.CommonDomain;

import java.util.Date;

/**
 * 统计类
 * @author meisen
 * 2019-07-26
 */
public class Statistics extends CommonDomain {

    // 用户访问统计
    private Long userVisit;
    // 每日用户范文
    private Long dailyUserVisit;
    // 统计时间
    private Date statisticTime;

    public Long getUserVisit() {
        return userVisit;
    }

    public void setUserVisit(Long userVisit) {
        this.userVisit = userVisit;
    }

    public Long getDailyUserVisit() {
        return dailyUserVisit;
    }

    public void setDailyUserVisit(Long dailyUserVisit) {
        this.dailyUserVisit = dailyUserVisit;
    }

    public Date getStatisticTime() {
        return statisticTime;
    }

    public void setStatisticTime(Date statisticTime) {
        this.statisticTime = statisticTime;
    }
}
