package com.epam.theater.service;

import com.epam.theater.entity.Role;
import com.epam.theater.entity.User;
import com.epam.theater.repository.RoleRepo;
import com.epam.theater.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepo.getOne(id);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void save(User user) {
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public List<User> findByUsernameIgnoreCaseOrMobile(String username, String mobile) {
        return userRepo.findByUsernameIgnoreCaseOrMobile(username, mobile);
    }

    @Override
    public void update(User user) {
        userRepo.saveAndFlush(user);
    }

    @Override
    public void saveList(List<User> elementList) {
        userRepo.saveAll(elementList);
    }

    @Override
    public User findUserByName(String username) {
        return userRepo.findByUsername(username);
    }
}
