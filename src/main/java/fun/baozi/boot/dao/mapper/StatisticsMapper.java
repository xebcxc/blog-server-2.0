package fun.baozi.boot.dao.mapper;

import fun.baozi.boot.domain.Statistics;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 * @author baozi
 * 2019-07-26
 */
@Repository
public interface StatisticsMapper extends Mapper<Statistics> {
    // 查询最新的统计数据
    Statistics searchLatest();
}