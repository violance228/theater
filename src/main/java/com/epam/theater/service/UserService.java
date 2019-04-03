package com.epam.theater.service;

import com.epam.theater.entity.User;

import java.util.List;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

public interface UserService extends BaseMethods<User> {
    User findUserByName(String username);
    User saveUser(User user);
    User findByEmail(String email);
    List<User> findByUsernameIgnoreCaseOrMobile(String username, String mobile);
}
