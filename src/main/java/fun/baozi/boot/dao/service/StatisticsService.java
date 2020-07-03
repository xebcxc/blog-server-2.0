package fun.baozi.boot.dao.service;

import fun.baozi.boot.domain.Statistics;
import fun.baozi.boot.dao.service.basic.BasicService;

public interface StatisticsService extends BasicService<Statistics> {

    Statistics searchLatest();
}
