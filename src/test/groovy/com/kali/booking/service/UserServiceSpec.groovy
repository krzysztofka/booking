package com.kali.booking.service

import com.kali.booking.exceptions.DataConflictException
import com.kali.booking.exceptions.EntityNotFoundException
import com.kali.booking.model.User
import com.kali.booking.model.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

class UserServiceSpec extends Specification {

    def userRepository = Mock(UserRepository.class)

    def userService = new UserService(userRepository)

    def user = Mock(User.class)

    def "should retrieve user"() {
        given:
        Long id = 0;

        when:
        def result = userService.getUser(id)

        then:
        userRepository.findOne(id) >> user
        result == user
    }

    def "should throw entity nof found when user null"() {
        given:
        Long id = 0;

        when:
        userService.getUser(id)

        then:
        userRepository.findOne(id) >> null

        then:
        thrown EntityNotFoundException
    }

    def "should throw data integrity exception"() {
        given:
        userRepository.save(user) >> {throw new DataIntegrityViolationException("exception")}

        when:
        userService.registerUser(user)

        then:
        thrown DataConflictException
    }
}
