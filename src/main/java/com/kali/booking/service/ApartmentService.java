package com.kali.booking.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.kali.booking.exception.DataConflictException;
import com.kali.booking.exception.EntityNotFoundException;
import com.kali.booking.model.ApartmentSearchCriteria;
import com.kali.booking.model.Hotel;
import com.kali.booking.model.Apartment;
import com.kali.booking.model.repository.ApartmentRepository;
import com.kali.booking.model.repository.ApartmentSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public List<Apartment> getAvailableApartments(ApartmentSearchCriteria searchCriteria) {
        Specification<Apartment> specification = toSpecification(searchCriteria);
        return apartmentRepository.findAll(specification);
    }

    private Specification<Apartment> toSpecification(ApartmentSearchCriteria searchCriteria) {
        List<Specification<Apartment>> specifications = Lists.newArrayList(
                ApartmentSpecifications.apartmentInCity(searchCriteria.getCity()),
                ApartmentSpecifications.priceFrom(searchCriteria.getPriceFrom()),
                ApartmentSpecifications.priceTo(searchCriteria.getPriceTo()),
                ApartmentSpecifications.bookingTime(searchCriteria.getFrom(), searchCriteria.getTo()));
        specifications.removeIf(Objects::isNull);

        Specifications<Apartment> specification = null;
        for (Specification<Apartment> s : specifications) {
            specification = specification == null ? Specifications.where(s) : specification.and(s);
        }

        return specification;

    }
}
