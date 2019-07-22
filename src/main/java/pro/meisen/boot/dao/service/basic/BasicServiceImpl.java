package pro.meisen.boot.dao.service.basic;

import pro.meisen.boot.domain.common.CommonDomain;
import pro.meisen.boot.dao.mapper.BasicMapper;

import java.util.Date;

/**
 * @author meisen
 * 2019-07-13
 */
public abstract class BasicServiceImpl<T extends CommonDomain> implements BasicService<T> {

    public abstract BasicMapper<Long, T> getMapper();

    @Override
    public T findById(Long id) {
        if (null == id) {
            return null;
        }
        return getMapper().findById(id);
    }

    @Override
    public int deleteById(Long id) {
        if (null == id) {
            return 0;
        }
        return getMapper().deleteById(id);
    }

    @Override
    public int update(T t) {
        if (null == t || null == t.getId()) {
            return 0;
        }
        t.setModifyTime(new Date());
        return getMapper().update(t);
    }

    @Override
    public int save(T t) {
        if (null == t) {
            return 0;
        }
        Date now = new Date();
        t.setCreateTime(now);
        t.setModifyTime(now);
        return getMapper().save(t);
    }

    @Override
    public Long selectCount(T condition) {
        return getMapper().selectCount(condition);
    }

    @Override
    public T selectOne(T condition) {
        return getMapper().selectOne(condition);
    }
}
