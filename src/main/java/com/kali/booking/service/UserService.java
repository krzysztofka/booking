package com.kali.booking.service;

import com.kali.booking.exceptions.DataConflictException;
import com.kali.booking.exceptions.EntityNotFoundException;
import com.kali.booking.model.User;
import com.kali.booking.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataConflictException("User with given email already exists");
        }
    }

    public User getUser(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findOne(id));
        return user.orElseThrow(EntityNotFoundException::new);
    }
}
