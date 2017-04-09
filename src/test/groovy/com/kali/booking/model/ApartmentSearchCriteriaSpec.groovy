package com.kali.booking.model

import org.apache.commons.lang3.RandomStringUtils


class ApartmentSearchCriteriaSpec extends ModelValidationSpecification {

    def searchCriteria = new ApartmentSearchCriteria()

    def setup() {
        searchCriteria.setPriceFrom(0)
        searchCriteria.setPriceTo(100000)
        searchCriteria.setCity("Warsaw")
        searchCriteria.setFrom(toDate(2217, 10, 10));
        searchCriteria.setTo(toDate(2217, 10, 12));
    }

    def "should pass validation when empty object"() {
        given:
        searchCriteria = new ApartmentSearchCriteria();

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        validationResult.isEmpty()
    }

    def "should pass validation"() {
        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        validationResult.isEmpty()
    }

    def "should fail validation when from is empty and to is filled"() {
        given:
        searchCriteria.setFrom(null)

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        !validationResult.isEmpty()
        assert validationResult[0].messageTemplate == "Date range invalid"
    }

    def "should fail validation when from is filled and to is empty"() {
        given:
        searchCriteria.setTo(null)

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        !validationResult.isEmpty()
        assert validationResult[0].messageTemplate == "Date range invalid"
    }

    def "should fail validation when from is not future"() {
        given:
        searchCriteria.setFrom(toDate(2016, 1, 1))

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], searchCriteria.getFrom(), "from")
    }

    def "should fail validation when city length is less then 2"() {
        given:
        searchCriteria.setCity("a")

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], searchCriteria.getCity(), "city")
    }

    def "should fail validation when city length is greater then 50"() {
        given:
        searchCriteria.setCity(RandomStringUtils.random(51))

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], searchCriteria.getCity(), "city")
    }

    def "should fail validation when priceFrom less then 0"() {
        given:
        searchCriteria.setPriceFrom(-1L)

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], searchCriteria.getPriceFrom(), "priceFrom")
    }

    def "should fail validation when priceTo is less then 0"() {
        given:
        searchCriteria.setPriceTo(-1L)

        when:
        def validationResult = validator.validate(searchCriteria)

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], searchCriteria.getPriceTo(), "priceTo")
    }
}
