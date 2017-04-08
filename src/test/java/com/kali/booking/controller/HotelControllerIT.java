package com.kali.booking.controller;

import com.jayway.restassured.http.ContentType;
import com.kali.booking.AbstractSpringIT;
import com.kali.booking.model.Hotel;
import com.kali.booking.model.Room;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Sql({"/sql/clean-up.sql", "/sql/hotel-test-data.sql"})
public class HotelControllerIT extends AbstractSpringIT {

    private static final String HOTEL_RESOURCE_PATH = "/booking-service/hotels";

    @Test
    public void shouldGetHotel() {
        Hotel result = when()
                .get(joinPaths(HOTEL_RESOURCE_PATH , "-10"))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Hotel.class);

        assertEquals("Hilton", result.getName());
        assertEquals("Warsaw", result.getCity());
        assertEquals(Long.valueOf(-10L), result.getId());
    }

    @Test
    public void shouldGet404WhenUserNotFound() {
        when()
                .get(joinPaths(HOTEL_RESOURCE_PATH , "-21212"))
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void shouldRegisterNewHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Hilton");
        hotel.setCity("Poznan");

        Hotel result = given()
                .contentType("application/json")
                .content(hotel)
                .when()
                .accept(ContentType.JSON)
                .post(HOTEL_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Hotel.class);

        assertEquals(hotel.getCity(), result.getCity());
        assertEquals(hotel.getName(), result.getName());
        assertTrue(hotel.getRooms().isEmpty());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotRegisterInvalidHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Name");
        hotel.setCity("a");

        given()
                .contentType("application/json")
                .content(hotel)
                .when()
                .accept(ContentType.JSON)
                .post(HOTEL_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void shouldNotRegisterADuplicateHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Hilton");
        hotel.setCity("Warsaw");

        given()
                .contentType("application/json")
                .content(hotel)
                .when()
                .accept(ContentType.JSON)
                .post(HOTEL_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    public void shouldGetHotelRoom() {
        Room result = when()
                .get(joinPaths(HOTEL_RESOURCE_PATH , "-10", "rooms", "-11"))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Room.class);

        assertEquals("Room 1", result.getName());
        assertEquals(Long.valueOf(10000L), result.getDailyPrice());
        assertEquals(Long.valueOf(-11L), result.getId());
        assertEquals(Long.valueOf(-10L), result.getHotel().getId());
    }


}
