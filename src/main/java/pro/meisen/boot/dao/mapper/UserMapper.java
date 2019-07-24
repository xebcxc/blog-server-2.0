package pro.meisen.boot.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.meisen.boot.domain.Comment;
import pro.meisen.boot.domain.User;

@Repository
public interface UserMapper extends BasicMapper<Long, User> {

    User findByUsername(@Param("username") String username);

    void updateUserStatus(@Param("id") Long id, @Param("isActive") boolean isActive);

}
