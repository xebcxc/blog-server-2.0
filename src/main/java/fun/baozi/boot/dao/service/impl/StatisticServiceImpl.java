package fun.baozi.boot.dao.service.impl;

import fun.baozi.boot.dao.mapper.StatisticsMapper;
import fun.baozi.boot.dao.service.StatisticsService;
import fun.baozi.boot.dao.service.basic.BasicServiceImpl;
import fun.baozi.boot.domain.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;


/**
 * 统计数据
 * @author baozi
 * 2019-07-26
 */
@Service
public class StatisticServiceImpl extends BasicServiceImpl<Statistics> implements StatisticsService {
    @Autowired
    private StatisticsMapper mapper;
    @Override
    public Mapper<Statistics> getArticleMapper() {
        return mapper;
    }

    @Override
    public Statistics searchLatest() {
        return mapper.searchLatest();
    }
}
