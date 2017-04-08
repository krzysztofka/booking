package com.kali.booking.controller;

import com.kali.booking.model.User;
import com.kali.booking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/users")
@RestController
@Validated
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Long id) {
        LOGGER.info("Get user: {}", id);

        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User registerUser(@RequestBody @Valid User user) {
        LOGGER.info("Register user: {}", user.getEmail());

        return userService.registerUser(user);
    }
}
