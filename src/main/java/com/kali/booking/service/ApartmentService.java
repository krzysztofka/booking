package com.kali.booking.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.kali.booking.exceptions.DataConflictException;
import com.kali.booking.exceptions.EntityNotFoundException;
import com.kali.booking.model.ApartmentSearchCriteria;
import com.kali.booking.model.Hotel;
import com.kali.booking.model.Apartment;
import com.kali.booking.model.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Apartment getApartment(Long apartmentId, Long hotelId) {
        Optional<Apartment> room = apartmentRepository.findByIdAndHotelId(apartmentId, hotelId);
        return room.orElseThrow(EntityNotFoundException::new);
    }

    public Apartment registerRoom(Hotel hotel, Apartment room) {
         Preconditions.checkArgument(room.getId() == null);
         room.setHotel(hotel);
         try {
             return apartmentRepository.save(room);
         } catch (DataIntegrityViolationException ex) {
             throw new DataConflictException("Hotel room with given name already exists");
         }
    }

    @Transactional
    public Page<Apartment> getAvailableApartments(ApartmentSearchCriteria searchCriteria, int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return apartmentRepository.getAvailableApartments(searchCriteria, pageable);
    }
}
