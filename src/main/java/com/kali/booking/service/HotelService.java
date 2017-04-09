package com.kali.booking.service;

import com.google.common.base.Preconditions;
import com.kali.booking.exception.DataConflictException;
import com.kali.booking.exception.EntityNotFoundException;
import com.kali.booking.model.Hotel;
import com.kali.booking.model.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel getHotel(Long id) {
        Optional<Hotel> hotel = Optional.ofNullable(hotelRepository.findOne(id));
        return hotel.orElseThrow(EntityNotFoundException::new);
    }

    public Hotel registerHotel(Hotel hotel) {
        Preconditions.checkArgument(hotel.getId() == null);
        try {
            return hotelRepository.save(hotel);
        } catch (DataIntegrityViolationException e) {
            throw new DataConflictException("Hotel with given name and city already exists");
        }
    }
}
