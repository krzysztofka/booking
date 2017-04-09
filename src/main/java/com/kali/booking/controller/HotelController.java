package com.kali.booking.controller;

import com.kali.booking.model.ApartmentSearchCriteria;
import com.kali.booking.model.Hotel;
import com.kali.booking.model.Apartment;
import com.kali.booking.service.HotelService;
import com.kali.booking.service.ApartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/hotels")
@RestController
@Validated
public class HotelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelController.class);

    private final HotelService hotelService;
    private final ApartmentService apartmentService;

    @Autowired
    public HotelController(HotelService hotelService, ApartmentService apartmentService) {
        this.hotelService = hotelService;
        this.apartmentService = apartmentService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Hotel getHotel(@PathVariable("id") Long id) {
        LOGGER.info("Get hotel: {}", id);
        return hotelService.getHotel(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Hotel registerHotel(@RequestBody @Valid Hotel hotel) {
        LOGGER.info("Register hotel: {} in {}", hotel.getName(), hotel.getCity());
        return hotelService.registerHotel(hotel);
    }

    @RequestMapping(value = "/{hotelId}/apartments/{id}", method = RequestMethod.GET)
    public Apartment getApartment(@PathVariable("hotelId") Long hotelId,
                                  @PathVariable("id") Long id) {
        LOGGER.info("Get apartment: {} for hotel: {} ", id, hotelId);
        return apartmentService.getApartment(id, hotelId);
    }

    @RequestMapping(value = "/{hotelId}/apartments", method = RequestMethod.POST)
    public Apartment registerApartment(@PathVariable("hotelId") Long hotelId,
                                       @RequestBody @Valid Apartment apartment) {
        LOGGER.info("Register apartment: {} in {}", apartment.getName(), hotelId);
        Hotel hotel = getHotel(hotelId);
        return apartmentService.registerRoom(hotel, apartment);
    }

    @RequestMapping(value = "/available-apartments", method = RequestMethod.GET)
    public List<Apartment> getAvailableApartments(@Valid ApartmentSearchCriteria apartmentSearchCriteria) {
        LOGGER.info("Get available apartments for criteria: {}", apartmentSearchCriteria);
        return apartmentService.getAvailableApartments(apartmentSearchCriteria);
    }
}
