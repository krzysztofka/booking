package com.kali.booking.model

import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import java.time.LocalDate
import java.time.ZoneId


class ModelValidationSpecification extends Specification {

    def validator = Validation.buildDefaultValidatorFactory().getValidator();

    def assertConstraintViolation(ConstraintViolation violation, Object value, String property) {
        assert property == violation.propertyPath.toString()
        assert value == violation.invalidValue
        return true
    }

    def Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
