package fun.baozi.boot.domain;

import fun.baozi.boot.domain.common.CommonDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 统计类
 * @author baozi
 * 2019-07-26
 */
@Table(name = "statistics")
public class Statistics extends CommonDomain {

    // 用户访问统计
    @Column(name = "user_visit")
    private Long userVisit;
    // 每日用户范文
    @Column(name = "daily_user_visit")
    private Long dailyUserVisit;
    // 统计时间
    @Column(name = "statistic_time")
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
