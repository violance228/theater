package com.epam.theater.controller;

import com.epam.theater.entity.User;
import com.epam.theater.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@RestController
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/save")
    public String save(@RequestBody String json) {
        Integer response = -1;
        User user = new Gson().fromJson(json, User.class);
        if (userService.findByUsernameIgnoreCaseOrMobile(user.getUsername(), user.getMobile()) == null) {
            if (userService.saveUser(user) != null)
                response = 1;
        } else
            response = 0;

        return response.toString();
    }

    @GetMapping(value = "/{userId}")
    public String get(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return new Gson().toJson(user);
    }

    @GetMapping(value = "/get/all")
    public String getAll() {
        List<User> user = userService.findAll();
        return new Gson().toJson(user);
    }

    @GetMapping(value = "/remove/{userId}")
    public String remove(@PathVariable Long userId) {
        userService.delete(userId);
        return "1";
    }

    @GetMapping(value = "/get/email/{email}")
    public String remove(@PathVariable String email) {
        return new Gson().toJson(userService.findByEmail(email));
    }
}
