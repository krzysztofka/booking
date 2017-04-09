package com.kali.booking.model


class BookingRequestSpec extends ModelValidationSpecification {

    def bookingRequest = new BookingRequest()

    def setup() {
        bookingRequest.setApartmentId(1L)
        bookingRequest.setUserId(1L)
        bookingRequest.setFrom(toDate(2217, 10, 10));
        bookingRequest.setTo(toDate(2217, 10, 12));
    }

    def "should pass validation"() {
        when:
        def validationResult = validator.validate(bookingRequest)

        then:
        validationResult.isEmpty()
    }

    def "should fail validation if from is not set"() {
        given:
        bookingRequest.setFrom(null)

        when:
        def validationResult = validator.validate(bookingRequest);

        then:
        validationResult.size() == 2;
    }

    def "should fail validation if to is not set"() {
        given:
        bookingRequest.setTo(null)

        when:
        def validationResult = validator.validate(bookingRequest)

        then:
        validationResult.size() == 2
    }

    def "should fail validation if from not in future"() {
        given:
        bookingRequest.setFrom(toDate(2016, 10, 12))

        when:
        def validationResult = validator.validate(bookingRequest);

        then:
        assert !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], bookingRequest.getFrom(), "from")
    }

    def "should fail validation if from after to"() {
        given:
        bookingRequest.setTo(toDate(2217, 10, 9));

        when:
        def validationResult = validator.validate(bookingRequest);

        then:
        !validationResult.isEmpty()
        assert validationResult[0].messageTemplate == "Date range invalid"
    }

    def "should fail validation if from the same as to"() {
        given:
        bookingRequest.setTo(toDate(2217, 10, 10));

        when:
        def validationResult = validator.validate(bookingRequest);

        then:
        !validationResult.isEmpty()
        assert validationResult[0].messageTemplate == "Date range invalid"
    }

    def "should fail validation if user not set"() {
        given:
        bookingRequest.setUserId(null)

        when:
        def validationResult = validator.validate(bookingRequest);

        then:
        !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], bookingRequest.getUserId(), "userId")
    }

    def "should fail validation if apartment id not set"() {
        given:
        bookingRequest.setApartmentId(null)

        when:
        def validationResult = validator.validate(bookingRequest);

        then:
        !validationResult.isEmpty()
        assertConstraintViolation(validationResult[0], bookingRequest.getApartmentId(), "apartmentId")
    }
}
