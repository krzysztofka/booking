package com.kali.booking.controller;

import com.jayway.restassured.http.ContentType;
import com.kali.booking.model.User;

import com.kali.booking.AbstractSpringIT;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class UserControllerIT extends AbstractSpringIT {

    @Test
    public void shouldRegisterNewUser() {
        User user = new User();
        user.setName("User name");
        user.setEmail("some@email.com");

        given()
                .contentType("application/json")
                .content(user)
        .when()
                .accept(ContentType.JSON)
                .post("/booking-service/user")
        .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
