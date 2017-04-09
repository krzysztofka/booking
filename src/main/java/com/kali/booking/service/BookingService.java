package com.kali.booking.service;

import com.kali.booking.exception.DataConflictException;
import com.kali.booking.exception.EntityNotFoundException;
import com.kali.booking.exception.InvalidRequestException;
import com.kali.booking.model.*;
import com.kali.booking.model.repository.ApartmentRepository;
import com.kali.booking.model.repository.BookingRepository;
import com.kali.booking.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return bookingRepository.findByIdAndStatusNot(id, BookingStatus.CANCELED)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserIdAndStatusNot(userId, BookingStatus.CANCELED);
    }

    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = getBooking(id);
        booking.setStatus(BookingStatus.CANCELED);
    }

    @Transactional
    public Booking registerBooking(BookingRequest bookingRequest) {
        User user = Optional.ofNullable(userRepository.findOne(bookingRequest.getUserId()))
                .orElseThrow(InvalidRequestException::new);

        Apartment apartment = apartmentRepository.findById(bookingRequest.getApartmentId())
                .orElseThrow(InvalidRequestException::new);

        checkIfBookingPossible(bookingRequest);

        Booking booking = new Booking();
        booking.setApartment(apartment);
        booking.setUser(user);
        booking.setFrom(bookingRequest.getFrom());
        booking.setTo(bookingRequest.getTo());
        booking.setStatus(BookingStatus.BOOKED);

        return bookingRepository.save(booking);
    }

    private void checkIfBookingPossible(BookingRequest bookingRequest) {
        if (bookingRepository.bookingCount(bookingRequest.getApartmentId(), bookingRequest.getFrom(), bookingRequest.getTo()) > 0) {
            throw new DataConflictException("Booking impossible");
        }
    }


}
