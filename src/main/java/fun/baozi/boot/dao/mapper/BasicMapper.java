package fun.baozi.boot.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author baozi
 * 2019-07-13
 */
public interface BasicMapper<K, T> {

    int deleteById(K id);

    T findById(K id);

    int update(T t);

    int save(T t);

    Long selectCount(@Param("condition")T condition);

    T selectOne(@Param("condition") T condition);

}
