package com.kali.booking.controller;

import com.jayway.restassured.http.ContentType;
import com.kali.booking.AbstractSpringIT;
import com.kali.booking.model.Booking;
import com.kali.booking.model.BookingRequest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Sql({"/sql/clean-up.sql", "/sql/booking-test-data.sql"})
public class BookingControllerIT extends AbstractSpringIT {

    private static final String BOOKING_RESOURCE_PATH = "/booking-service/bookings";

    @Test
    public void shouldGetBooking() {
        Booking result = when()
                .get(joinPaths(BOOKING_RESOURCE_PATH , "-1"))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Booking.class);

        assertEquals(Long.valueOf(-1L), result.getUser().getId());
        assertEquals(Long.valueOf(-1L), result.getApartment().getId());
        assertEquals(toDate(2017, 10, 10), result.getFrom());
        assertEquals(toDate(2017, 10, 12), result.getTo());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldRegisterNewBooking() {
        BookingRequest request = new BookingRequest(-1L, -1L, toDate(2017, 10, 12), toDate(2017, 10, 14));

        Booking result = given()
                .contentType("application/json")
                .content(request)
                .when()
                .accept(ContentType.JSON)
                .post(BOOKING_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Booking.class);

        assertEquals(request.getUserId(), result.getUser().getId());
        assertEquals(request.getApartmentId(), result.getApartment().getId());
        assertEquals(request.getFrom(), result.getFrom());
        assertEquals(request.getTo(), result.getTo());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotRegisterBookingWhenTimeOverlap() {
        BookingRequest request = new BookingRequest(-1L, -1L, toDate(2017, 10, 11), toDate(2017, 10, 14));

        given()
                .contentType("application/json")
                .content(request)
                .when()
                .accept(ContentType.JSON)
                .post(BOOKING_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    public void shouldGetUserBookings() {
        //TODO
    }

}
