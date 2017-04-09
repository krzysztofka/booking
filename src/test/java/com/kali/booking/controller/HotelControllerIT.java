package com.kali.booking.controller;

import com.jayway.restassured.http.ContentType;
import com.kali.booking.AbstractSpringIT;
import com.kali.booking.model.Hotel;
import com.kali.booking.model.Apartment;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Sql({"/sql/clean-up.sql", "/sql/hotel-test-data.sql"})
public class HotelControllerIT extends AbstractSpringIT {

    private static final String HOTEL_RESOURCE_PATH = "/booking-service/hotels";

    private static final String HOTEL_ROOM_RESOURCE_PATH = "/booking-service/hotels/{hotelId}/apartments";

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
        Apartment result = when()
                .get(joinPaths(HOTEL_ROOM_RESOURCE_PATH, "-11"), -10L)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Apartment.class);

        assertEquals("Room 1", result.getName());
        assertEquals(Long.valueOf(10000L), result.getDailyPrice());
        assertEquals(Long.valueOf(-11L), result.getId());
        assertEquals(Long.valueOf(-10L), result.getHotel().getId());
    }

    @Test
    public void shouldGet404WhenHotelDoesNotExist() {
        when()
                .get(joinPaths(HOTEL_ROOM_RESOURCE_PATH, "-11"), -11L)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void shouldGet404WhenRoomDoesNotExist() {
        when()
                .get(joinPaths(HOTEL_ROOM_RESOURCE_PATH, "-1"), -10L)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void shouldRegisterNewHotelRoom() {
        Apartment room = new Apartment();
        room.setName("New room");
        room.setDailyPrice(1000L);

        Apartment result = given()
                .contentType("application/json")
                .content(room)
                .when()
                .accept(ContentType.JSON)
                .post(HOTEL_ROOM_RESOURCE_PATH, -10L)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Apartment.class);

        assertEquals(Long.valueOf(-10L), result.getHotel().getId());
        assertEquals(room.getName(), result.getName());
        assertEquals(room.getDailyPrice(), result.getDailyPrice());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotRegisterADuplicateRoom() {
        Apartment room = new Apartment();
        room.setName("Room 1");
        room.setDailyPrice(1000L);

        given()
                .contentType("application/json")
                .content(room)
                .when()
                .accept(ContentType.JSON)
                .post(HOTEL_ROOM_RESOURCE_PATH, -10L)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    public void shouldNotRegisterInvalidRoom() {
        Apartment room = new Apartment();
        room.setName("Name");
        room.setDailyPrice(-100L);

        given()
                .contentType("application/json")
                .content(room)
                .when()
                .accept(ContentType.JSON)
                .post(HOTEL_ROOM_RESOURCE_PATH,  -10L)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void shouldNotRegisterRoomToNonExistingHotel() {
        Apartment room = new Apartment();
        room.setName("Name");
        room.setDailyPrice(100L);

        given()
                .contentType("application/json")
                .content(room)
                .when()
                .accept(ContentType.JSON)
                .post(HOTEL_ROOM_RESOURCE_PATH,  -100000L)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void shouldGetAvailableApartments() {
        String city = "Warsaw";

        Apartment[] apartments = given()
                .queryParam("city", city)
                .queryParam("priceFrom", 10000)
                .queryParam("priceTo", 12000L)
                .queryParam("from", "2160-10-10")
                .queryParam("to", "2160-10-12")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .accept(ContentType.JSON)
                .get(joinPaths(HOTEL_RESOURCE_PATH, "available-apartments"))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Apartment[].class);

        Assert.assertEquals(1, apartments.length);
        Assert.assertEquals(Long.valueOf(-11), apartments[0].getId());
        Assert.assertEquals("Room 1", apartments[0].getName());
        Assert.assertEquals(Long.valueOf(10000L), apartments[0].getDailyPrice());
        Assert.assertEquals(city, apartments[0].getHotel().getCity());
    }
}