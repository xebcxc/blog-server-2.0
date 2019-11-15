package pro.meisen.boot.dao.service.basic;

import pro.meisen.boot.domain.common.CommonDomain;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @author meisen
 * 2019-07-13
 */
public abstract class BasicServiceImpl<T extends CommonDomain> implements BasicService<T> {

    public abstract Mapper<T> getArticleMapper();

    @Override
    public T findById(Long id) {
        if (null == id) {
            return null;
        }
        return getArticleMapper().selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(Long id) {
        if (null == id) {
            return 0;
        }
        return getArticleMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        if (null == t || null == t.getId()) {
            return 0;
        }
        t.setModifyTime(new Date());
        return getArticleMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public int insertSelective(T t) {
        if (null == t) {
            return 0;
        }
        Date now = new Date();
        t.setCreateTime(now);
        t.setModifyTime(now);
        return getArticleMapper().insertSelective(t);
    }

    @Override
    public int selectCount(T condition) {
        return getArticleMapper().selectCount(condition);
    }

    @Override
    public T selectOne(T condition) {
        return getArticleMapper().selectOne(condition);
    }
}
