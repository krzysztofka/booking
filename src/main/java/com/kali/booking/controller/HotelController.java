package com.kali.booking.controller;

import com.kali.booking.model.Hotel;
import com.kali.booking.model.Room;
import com.kali.booking.service.HotelService;
import com.kali.booking.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/hotels")
@RestController
@Validated
public class HotelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelController.class);

    private final HotelService hotelService;
    private final RoomService roomService;

    @Autowired
    public HotelController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
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

    @RequestMapping(value = "/{hotelId}/rooms/{id}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable("hotelId") Long hotelId, @PathVariable("id") Long id) {
        LOGGER.info("Get room: {} for hotel: {} ", id, hotelId);
        return roomService.getRoom(id, hotelId);
    }

    @RequestMapping(value = "/{hotelId}/rooms", method = RequestMethod.POST)
    public Room registerRoom(@PathVariable("hotelId") Long hotelId, @RequestBody @Valid Room room) {
        LOGGER.info("Register room: {} in {}", room.getName(), hotelId);
        Hotel hotel = getHotel(hotelId);
        return roomService.registerRoom(hotel, room);
    }

    @RequestMapping(value = "/available-rooms", method = RequestMethod.GET)
    public List<Room> availableRooms(@RequestParam(value = "form", required = false) @Future Date from, // >= current date
                                     @RequestParam(required = false) @Future Date to, // future date
                                     @RequestParam(value = "city", required = false) @Size(min = 2, max = 50) String city,
                                     @RequestParam(value = "priceTo", required = false) Long priceFrom, // positive
                                     @RequestParam(value = "priceFrom", defaultValue = "0") Long priceTo)  { // positive
        return new ArrayList<>();
    }
}
