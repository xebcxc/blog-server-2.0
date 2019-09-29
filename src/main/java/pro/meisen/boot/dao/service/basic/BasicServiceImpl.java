package pro.meisen.boot.dao.service.basic;

import pro.meisen.boot.domain.common.CommonDomain;
import pro.meisen.boot.dao.mapper.BasicMapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @author meisen
 * 2019-07-13
 */
public abstract class BasicServiceImpl<T extends CommonDomain> implements BasicService<T> {

    public abstract Mapper<T> getMapper();

    @Override
    public T findById(Long id) {
        if (null == id) {
            return null;
        }
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(Long id) {
        if (null == id) {
            return 0;
        }
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        if (null == t || null == t.getId()) {
            return 0;
        }
        t.setModifyTime(new Date());
        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public int insertSelective(T t) {
        if (null == t) {
            return 0;
        }
        Date now = new Date();
        t.setCreateTime(now);
        t.setModifyTime(now);
        return getMapper().insertSelective(t);
    }

    @Override
    public int selectCount(T condition) {
        return getMapper().selectCount(condition);
    }

    @Override
    public T selectOne(T condition) {
        return getMapper().selectOne(condition);
    }
}
