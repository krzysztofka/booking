package com.kali.booking.controller;

import com.jayway.restassured.http.ContentType;
import com.kali.booking.model.User;

import com.kali.booking.AbstractSpringIT;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.*;

@Sql({"/sql/clean-up.sql", "/sql/user-test-data.sql"} )
public class UserControllerIT extends AbstractSpringIT {

    private static final String USER_RESOURCE_PATH = "/booking-service/users";

    @Test
    public void shouldGetUser() {
        User result = when()
                .get(joinPaths(USER_RESOURCE_PATH , "-1"))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(User.class);

        assertEquals("email-test@test.com", result.getEmail());
        assertEquals("some name", result.getName());
        assertEquals(Long.valueOf(-1L), result.getId());
    }

    @Test
    public void shouldGet404WhenUserNotFound() {
        when()
                .get(joinPaths(USER_RESOURCE_PATH , "-2"))
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void shouldRegisterNewUser() {
        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("jan.kowalski@gmail.com");

        User result = given()
                .contentType("application/json")
                .content(user)
                .when()
                .accept(ContentType.JSON)
                .post(USER_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(User.class);

        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getName(), result.getName());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotRegisterInvalidUser() {
        User user = new User();
        user.setName("User name");
        user.setEmail("invalid email");

        given()
                .contentType("application/json")
                .content(user)
                .when()
                .accept(ContentType.JSON)
                .post(USER_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void shouldNotRegisterADuplicateUser() {
        User user = new User();
        user.setName("User name");
        user.setEmail("email-test@test.com");

        given()
                .contentType("application/json")
                .content(user)
                .when()
                .accept(ContentType.JSON)
                .post(USER_RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);
    }
}
