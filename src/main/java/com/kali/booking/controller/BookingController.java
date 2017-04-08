package com.kali.booking.controller;

import com.kali.booking.model.Booking;
import com.kali.booking.model.BookingRequest;
import com.kali.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/bookings")
public class BookingController extends AbstractRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Booking registerBooking(@RequestBody @Valid BookingRequest bookingRequest) {
        LOGGER.info("Register new booking: {}", bookingRequest);
        return bookingService.registerBooking(bookingRequest);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Booking getBooking(@PathVariable Long id) {
        LOGGER.info("Get booking: {}", id);
        return bookingService.getBooking(id);
    }

    @RequestMapping(params = {"userId"}, method = RequestMethod.GET)
    public List<Booking> getUserBookings(@RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam("userId") Long userId) {
        //TODO hateoas
        LOGGER.info("Get user booking: {}", userId);
        return bookingService.getUserBookings(userId, page, size).getContent();
    }
}
