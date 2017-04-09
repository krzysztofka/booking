package com.kali.booking.model.validations;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private static Logger LOGGER = LoggerFactory.getLogger(DateRangeValidator.class);

    private String fromProperty;
    private String toProperty;

    @Override
    public void initialize(final DateRange constraintAnnotation) {
        fromProperty = constraintAnnotation.from();
        toProperty = constraintAnnotation.to();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Date fromDate = (Date) FieldUtils.readDeclaredField(value, fromProperty, true);
            final Date toDate = (Date) FieldUtils.readDeclaredField(value, toProperty, true);

            return checkBothSetOrNull(fromDate, toDate) && checkFromBeforeTo(fromDate, toDate);
        } catch (final IllegalAccessException ex) {
            LOGGER.error("Invalid DateRange configuration for class: " + value.getClass(), ex);
            return false;
        }
    }

    private boolean checkBothSetOrNull(Date from, Date to) {
        return from == null && to == null || from != null && to != null;
    }

    private boolean checkFromBeforeTo(Date from, Date to) {
        return from == null || from.before(to);
    }
}
