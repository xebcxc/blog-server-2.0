package pro.meisen.boot.dao.service;

import pro.meisen.boot.dao.service.basic.BasicService;
import pro.meisen.boot.domain.Statistics;

public interface StatisticsService extends BasicService<Statistics> {

    Statistics searchLatest();
}
