package com.kali.booking.model.repository;

import com.kali.booking.model.Booking;
import com.kali.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {

    @Query("select count(r) from Booking r where r.status != 'CANCELED' and r.apartment.id = :apartmentId " +
            "and ((r.from >= :from and r.from < :to) or (r.to > :from and r.to <= :to))")
    long bookingCount(@Param("apartmentId") Long apartmentId, @Param("from") Date from, @Param("to") Date to);

    Optional<Booking> findByIdAndStatusNot(Long id, BookingStatus status);

    List<Booking> findByUserIdAndStatusNot(Long userId, BookingStatus status);
}
