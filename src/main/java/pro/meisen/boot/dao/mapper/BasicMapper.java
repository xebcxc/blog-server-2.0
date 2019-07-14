package pro.meisen.boot.dao.mapper;

/**
 * @author meisen
 * 2019-07-13
 */
public interface BasicMapper<K, T> {

    int deleteById(K id);

    T findById(K id);

    int update(T t);

    int save(T t);

}
