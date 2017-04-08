package com.kali.booking.model

import org.apache.commons.lang3.RandomStringUtils

class UserValidationSpec extends ModelValidationSpecification {

    def user = new User()

    def setup() {
        user = new User()
        user.setName("Some name")
        user.setEmail("email@email.com")
    }

    def "should pass user validation"() {
        when:
        def validationResult = validator.validate(user)

        then:
        validationResult.isEmpty()
    }

    def "should fail validation when email invalid"() {
        given:
        user.setEmail("invalid@")

        when:
        def validationResult = validator.validate(user);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], user.getEmail(), "email")
    }

    def "should fail when email blank"() {
        given:
        user.setEmail(" ")

        when:
        def validationResult = validator.validate(user);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], user.getEmail(), "email")
    }

    def "should fail when email is null"() {
        given:
        user.setEmail(null)

        when:
        def validationResult = validator.validate(user);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], user.getEmail(), "email")
    }

    def "should fail when name blank"() {
        given:
        user.setName(" ")

        when:
        def validationResult = validator.validate(user);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], user.getName(), "name")
    }

    def "should fail when name size less then 2"() {
        given:
        user.setName("a")

        when:
        def validationResult = validator.validate(user);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], user.getName(), "name")
    }

    def "should fail when name length is higher then 50"() {
        given:
        user.setName(RandomStringUtils.random(51))

        when:
        def validationResult = validator.validate(user)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], user.getName(), "name")
    }

    def "should fail when name is null"() {
        given:
        user.setName(null)

        when:
        def validationResult = validator.validate(user);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], user.getName(), "name")
    }
}
