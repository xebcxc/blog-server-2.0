package pro.meisen.boot.dao.service;

import pro.meisen.boot.dao.service.basic.BasicService;
import pro.meisen.boot.domain.User;

/**
 * @author meisen
 * 2019-07-22
 */
public interface UserService extends BasicService<User> {

    User findByUsername(String username);

    void updateUserStatus(Long id, boolean isActive);
}
