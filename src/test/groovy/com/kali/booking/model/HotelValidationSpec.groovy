package com.kali.booking.model

import org.apache.commons.lang3.RandomStringUtils

class HotelValidationSpec extends ModelValidationSpecification {

    def hotel = new Hotel()

    def setup() {
        hotel = new Hotel()
        hotel.setName("Some name")
        hotel.setCity("some city")
    }

    def "should pass hotel validation"() {
        when:
        def validationResult = validator.validate(hotel)

        then:
        validationResult.isEmpty()
    }

    def "should fail when name blank"() {
        given:
        hotel.setName(" ")

        when:
        def validationResult = validator.validate(hotel);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getName(), "name")
    }

    def "should fail when name size less then 2"() {
        given:
        hotel.setName("a")

        when:
        def validationResult = validator.validate(hotel);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getName(), "name")
    }

    def "should fail when name length is higher then 50"() {
        given:
        hotel.setName(RandomStringUtils.random(51))

        when:
        def validationResult = validator.validate(hotel)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getName(), "name")
    }

    def "should fail when name is null"() {
        given:
        hotel.setName(null)

        when:
        def validationResult = validator.validate(hotel);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getName(), "name")
    }

    def "should fail when city blank"() {
        given:
        hotel.setCity(" ")

        when:
        def validationResult = validator.validate(hotel);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getCity(), "city")
    }

    def "should fail when city size less then 2"() {
        given:
        hotel.setCity("a")

        when:
        def validationResult = validator.validate(hotel);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getCity(), "city")
    }

    def "should fail when city length is higher then 50"() {
        given:
        hotel.setCity(RandomStringUtils.random(51))

        when:
        def validationResult = validator.validate(hotel)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getCity(), "city")
    }

    def "should fail when city is null"() {
        given:
        hotel.setCity(null)

        when:
        def validationResult = validator.validate(hotel);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], hotel.getCity(), "city")
    }
}
