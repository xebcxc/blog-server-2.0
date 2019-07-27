package pro.meisen.boot.dao.mapper;

import org.springframework.stereotype.Repository;
import pro.meisen.boot.domain.Statistics;

/**
 *
 * @author meisen
 * 2019-07-26
 */
@Repository
public interface StatisticsMapper extends BasicMapper<Long, Statistics> {
    // 查询最新的统计数据
    Statistics searchLatest();
}