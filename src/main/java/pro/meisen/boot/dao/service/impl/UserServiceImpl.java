package pro.meisen.boot.dao.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.meisen.boot.dao.mapper.BasicMapper;
import pro.meisen.boot.dao.mapper.UserMapper;
import pro.meisen.boot.dao.service.UserService;
import pro.meisen.boot.dao.service.basic.BasicServiceImpl;
import pro.meisen.boot.domain.User;

/**
 * @author meisen
 * 2019-07-22
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public BasicMapper<Long, User> getMapper() {
        return mapper;
    }

    @Override
    public User findByUsername(String username) {
        if (Strings.isEmpty(username)) {
            return null;
        }
        return mapper.findByUsername(username);
    }

    @Override
    public void updateUserStatus(Long id, boolean isActive) {
        if (null == id) {
            return;
        }
        mapper.updateUserStatus(id, isActive);
    }

}
