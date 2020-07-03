package fun.baozi.boot.dao.service.impl;

import fun.baozi.boot.dao.mapper.UserMapper;
import fun.baozi.boot.dao.service.UserService;
import fun.baozi.boot.domain.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fun.baozi.boot.dao.service.basic.BasicServiceImpl;
import tk.mybatis.mapper.common.Mapper;

import java.util.Objects;

/**
 * @author baozi
 * 2019-07-22
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public Mapper<User> getArticleMapper() {
        return mapper;
    }

    @Override
    public User findByUsername(String username) {
        if (Strings.isEmpty(username)) {
            return null;
        }
        User record = new User();
        record.setUsername(username);
        return mapper.selectOne(record);
    }

    @Override
    public void updateUserStatus(Long id, boolean isActive) {
        if (Objects.isNull(id)) {
            return;
        }
        User condition = new User();
        condition.setId(id);
        condition.setActive(isActive);
        mapper.updateByPrimaryKeySelective(condition);
    }

}
