package pro.meisen.boot.dao.mapper;

import org.springframework.stereotype.Repository;
import pro.meisen.boot.domain.User;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {

}
