package com.kali.booking.model

import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation


class ModelValidationSpecification extends Specification {

    def validator = Validation.buildDefaultValidatorFactory().getValidator();

    def assertConstraintViolation(ConstraintViolation violation, Object value, String property) {
        assert property == violation.propertyPath.toString()
        assert value == violation.invalidValue
        return true
    }
}
