# booking [![Build Status](https://travis-ci.org/krzysztofka/booking.svg)](http://travis-ci.org/krzysztofka/booking?branch=master)

What is it?
-----------

Booking is a simple Spring Boot application solving simple scenario of hotel apartment reservation.


Current functioanllity
-----------------------

* Hotel registration
* Apartment registration
* Customer registration
* Listing of available apartments using search criteria
  * booking period
  * city
  * daily price range
* Apartment reservation
* Listing of user reservations
* Reservation cancellation

Requirements
------------

Java 1.8

Maven 3.x

Installation
------------

`mvn clean package` - builds spring boot executable jar

`java -jar target/booking-service-VERSION.jar` - runs tomcat with embedded H2 database

API
---
For now The best way to check API is via rest controller acceptance tests:
* `com.kali.booking.controller.BookingControllerIT`
* `com.kali.booking.controller.HotelControllerIT`
* `com.kali.booking.controller.UserControllerIT`

DATABASE
--------
Booking uses embedded H2 database.
You can access h2 console via `http://localhost:7000/booking-service/h2-console`

CURL's
------

* USER
`curl -H "Content-Type: application/json" -d '{"email": "test@mail.com", "name": "John Doe"}' -X POST "http://localhost:7000/booking-service/users"` Registers user
`curl "http://localhost:7000/booking-service/users/1"` Finds previously created user

* HOTEL
`curl -H "Content-Type: application/json" -d '{"city": "Warsaw", "name": "Hilton"}' -X POST "http://localhost:7000/booking-service/hotels"` Registers hotel
`curl "http://localhost:7000/booking-service/hotels/1"` Find previously created hotel

* APARTMENT
`curl -H "Content-Type: application/json" -d '{"name": "Room 1", "dailyPrice": 10000}' -X POST "http://localhost:7000/booking-service/hotels/1/apartments"` Registers apartment
`curl "http://localhost:7000/booking-service/hotels/1/apartments/1"` Get previously created apartment
`curl "http://localhost:7000/booking-service/hotels/available-apartments"` Get available apartments
`curl "http://localhost:7000/booking-service/hotels/available-apartments?city=Warsaw&priceFrom=8000&priceTo=120000"` Get available apartments for given city and price range
`curl "http://localhost:7000/booking-service/hotels/available-apartments?from=2020-10-10&to=2020-12-12"` Get available apartments for given period

* BOOKING
`curl -H "Content-Type: application/json" -d '{"userId": 1, "apartmentId": 1, "from": "2020-10-10", "to": "2020-12-12"}' -X POST "http://localhost:7000/booking-service/bookings"` Create a booking
`curl "http://localhost:7000/booking-service/bookings?userId=1"` Get user bookings
`curl -X DELETE "http://localhost:7000/booking-service/bookings/1"` Delete previously created booking

TODO
----
* Add swagger
* Add resource pagination
* Add security