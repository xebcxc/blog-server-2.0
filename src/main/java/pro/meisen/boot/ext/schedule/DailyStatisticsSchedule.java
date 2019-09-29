package pro.meisen.boot.ext.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.meisen.boot.dao.service.StatisticsService;
import pro.meisen.boot.domain.Statistics;
import pro.meisen.boot.ext.redis.RedisKey;
import pro.meisen.boot.ext.redis.RedisOperation;

import java.util.Date;

/**
 * @author meisen
 * 2019-07-26
 */
@Component
public class DailyStatisticsSchedule {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private RedisOperation<String> redisOperation;

    @Scheduled(cron = "0 56 23 * * ?")
    public void statisticsSchedule() {
        Statistics statistics = statisticsService.searchLatest();
        Long userVisit = 0L;
        if (null != statistics) {
            userVisit = statistics.getUserVisit();
        }
        Long logCount = redisOperation.pdCount(RedisKey.UV.getKey());
        statistics = new Statistics();
        statistics.setStatisticTime(new Date());
        statistics.setDailyUserVisit(logCount - userVisit);
        statistics.setUserVisit(logCount);
        statisticsService.insertSelective(statistics);
    }
}
