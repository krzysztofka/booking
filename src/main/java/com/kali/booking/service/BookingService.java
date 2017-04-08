package com.kali.booking.service;

import com.kali.booking.exceptions.DataConflictException;
import com.kali.booking.exceptions.EntityNotFoundException;
import com.kali.booking.exceptions.InvalidRequestException;
import com.kali.booking.model.Apartment;
import com.kali.booking.model.Booking;
import com.kali.booking.model.BookingRequest;
import com.kali.booking.model.User;
import com.kali.booking.model.repository.ApartmentRepository;
import com.kali.booking.model.repository.BookingRepository;
import com.kali.booking.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookingService {

    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(UserRepository userRepository, ApartmentRepository apartmentRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.apartmentRepository = apartmentRepository;
        this.bookingRepository = bookingRepository;
    }

    public Booking getBooking(Long id) {
        return Optional.ofNullable(bookingRepository.findOne(id))
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<Booking> getUserBookings(Long userId, int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return bookingRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public Booking registerBooking(BookingRequest bookingRequest) {
        User user = Optional.ofNullable(userRepository.findOne(bookingRequest.getUserId()))
                .orElseThrow(InvalidRequestException::new);

        Apartment apartment = apartmentRepository.findById(bookingRequest.getApartmentId())
                .orElseThrow(InvalidRequestException::new);

        //TODO: test lock
        checkIfBookingPossible(bookingRequest);

        Booking booking = new Booking();
        booking.setApartment(apartment);
        booking.setUser(user);
        booking.setFrom(bookingRequest.getFrom());
        booking.setTo(bookingRequest.getTo());

        return bookingRepository.save(booking);
    }

    private void checkIfBookingPossible(BookingRequest bookingRequest) {
        if (bookingRepository.bookingCount(bookingRequest.getApartmentId(), bookingRequest.getFrom(), bookingRequest.getTo()) > 0) {
            throw new DataConflictException("Booking impossible");
        }
    }


}
