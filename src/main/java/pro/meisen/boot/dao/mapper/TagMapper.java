package pro.meisen.boot.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.meisen.boot.domain.Tag;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TagMapper extends Mapper<Tag> {
    // 更具id获取集合
    List<Tag> listByIds(@Param("idList") List<Long> idList);

    List<Tag> listByTagNameList(@Param("tagNameList") List<String> tagNameList);

    int batchInsert(@Param("tagList") List<Tag> tagList);

    int batchUpdate(@Param("tagList") List<Tag> tagList);
}