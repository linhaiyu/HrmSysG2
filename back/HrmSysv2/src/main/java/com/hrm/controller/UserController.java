package com.hrm.controller;

import com.hrm.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(Integer id) {
        User user = new User();
        logger.info("user={}", user);
        return user;
    }
}
