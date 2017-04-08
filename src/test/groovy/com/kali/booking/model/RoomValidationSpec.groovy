package com.kali.booking.model

import org.apache.commons.lang3.RandomStringUtils


class RoomValidationSpec extends ModelValidationSpecification {

    def room = new Room()

    def setup() {
        room = new Room()
        room.setName("Some name")
        room.setDailyPrice(0L)
    }

    def "should pass room validation"() {
        when:
        def validationResult = validator.validate(room)

        then:
        validationResult.isEmpty()
    }

    def "should fail when name blank"() {
        given:
        room.setName(" ")

        when:
        def validationResult = validator.validate(room)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], room.getName(), "name")
    }

    def "should fail when name size less then 2"() {
        given:
        room.setName("a")

        when:
        def validationResult = validator.validate(room);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], room.getName(), "name")
    }

    def "should fail when name length is higher then 30"() {
        given:
        room.setName(RandomStringUtils.random(31))

        when:
        def validationResult = validator.validate(room)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], room.getName(), "name")
    }

    def "should fail when name is null"() {
        given:
        room.setName(null)

        when:
        def validationResult = validator.validate(room);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], room.getName(), "name")
    }

    def "should fail when daily price null"() {
        given:
        room.setDailyPrice(null)

        when:
        def validationResult = validator.validate(room);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], room.getDailyPrice(), "dailyPrice")
    }

    def "should fail when daily price is negative"() {
        given:
        room.setDailyPrice(-1L)

        when:
        def validationResult = validator.validate(room)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], room.getDailyPrice(), "dailyPrice")
    }

}
