# booking


USER
curl -H "Content-Type: application/json" -d '{"email": "test@mail.com", "name": "John Doe"}' -X POST "http://localhost:7000/booking-service/users"
curl "http://localhost:7000/booking-service/users/1"

HOTEL
curl -H "Content-Type: application/json" -d '{"city": "Warsaw", "name": "Hilton"}' -X POST "http://localhost:7000/booking-service/hotels"
curl "http://localhost:7000/booking-service/hotels/1"

APARTMENT
curl -H "Content-Type: application/json" -d '{"name": "Room 1", "dailyPrice": 10000}' -X POST "http://localhost:7000/booking-service/hotels/1/apartments"
curl "http://localhost:7000/booking-service/hotels/1/apartments/1"
curl "http://localhost:7000/booking-service/hotels/available-apartments"
curl "http://localhost:7000/booking-service/hotels/available-apartments?city=Warsaw&priceFrom=8000&priceTo=120000"
curl "http://localhost:7000/booking-service/hotels/available-apartments?from=2020-10-10&to=2020-12-12"

BOOKING
curl -H "Content-Type: application/json" -d '{"userId": 1, "apartmentId": 1, "from": "2020-10-10", "to": "2020-12-12"}' -X POST "http://localhost:7000/booking-service/bookings"
curl -H "Content-Type: application/json" -d '{"userId": 1, "apartmentId": 1, "from": "2020-10-08", "to": "2020-10-10"}' -X POST "http://localhost:7000/booking-service/bookings"
curl "http://localhost:7000/booking-service/bookings?userId=1"
curl -X DELETE "http://localhost:7000/booking-service/bookings/1"