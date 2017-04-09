package com.kali.booking.model.repository;

import com.kali.booking.model.Apartment;

import com.kali.booking.model.Booking;
import com.kali.booking.model.BookingStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;

public final class ApartmentSpecifications {

    private ApartmentSpecifications() {
    }

    public static Specification<Apartment> apartmentInCity(String city) {
        return city != null ? (apartment, query, cb) -> cb.equal(apartment.get("hotel").get("city"), city) : null;
    }

    public static Specification<Apartment> priceFrom(Long from) {
        return from != null ? (apartment, query, cb) -> cb.ge(apartment.get("dailyPrice"), from) : null;
    }

    public static Specification<Apartment> priceTo(Long to) {
        return to != null ? (apartment, query, cb) -> cb.lt(apartment.get("dailyPrice"), to) : null;
    }

    public static Specification<Apartment> bookingTime(Date from, Date to) {
        return to == null ? null : (apartment, query, cb) -> {
            Subquery<Long> bookingSubQuery = query.subquery(Long.class);
            Root<Booking> booking = bookingSubQuery.from(Booking.class);

            bookingSubQuery.select(booking.get("apartment").get("id"));

            bookingSubQuery.where(
                    cb.notEqual(booking.get("status"), BookingStatus.CANCELED),
                    cb.greaterThan(booking.get("to"), from),
                    cb.lessThan(booking.get("from"), to));

            return cb.in(apartment.get("id")).value(bookingSubQuery).not();
        };
    }

}
