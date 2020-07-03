package fun.baozi.boot.dao.service.basic;

import fun.baozi.boot.domain.common.CommonDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baozi
 * 2019-07-13
 */
public interface BasicService<T extends CommonDomain> {

    T findById(Long id);

    int deleteById(Long id);

    int updateByPrimaryKeySelective(T t);

    int insertSelective(T t);

    int selectCount(T condition);

    T selectOne(T condition);

    default List<T> notEmptyList(List<T> list) {
        return null == list || list.isEmpty() ? new ArrayList<>() : list;
    }
}
