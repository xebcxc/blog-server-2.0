package pro.meisen.boot.dao.service;

import pro.meisen.boot.domain.CommonDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author meisen
 * 2019-07-13
 */
public interface BasicService<T extends CommonDomain> {

    T findById(Long id);

    int deleteById(Long id);

    int update(T t);

    int save(T t);

    default List<T> notEmptyList(List<T> list) {
        return null == list || list.isEmpty() ? new ArrayList<>() : list;
    }
}
