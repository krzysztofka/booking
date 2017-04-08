package com.kali.booking.model.repository;

import com.kali.booking.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {

    @Query("select count(r) from Booking r where r.apartment.id = :apartmentId and ((r.from >= :from and r.from < :to) or (r.to > :from and r.to <= :to))")
    long bookingCount(@Param("apartmentId") Long apartmentId, @Param("from") Date from, @Param("to") Date to);

    Page<Booking> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
