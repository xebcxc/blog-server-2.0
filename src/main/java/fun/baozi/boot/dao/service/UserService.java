package fun.baozi.boot.dao.service;

import fun.baozi.boot.domain.User;
import fun.baozi.boot.dao.service.basic.BasicService;

/**
 * @author baozi
 * 2019-07-22
 */
public interface UserService extends BasicService<User> {

    User findByUsername(String username);

    void updateUserStatus(Long id, boolean isActive);
}
