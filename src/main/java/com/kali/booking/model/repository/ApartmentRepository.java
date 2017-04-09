package com.kali.booking.model.repository;

import com.kali.booking.model.Apartment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ApartmentRepository extends PagingAndSortingRepository<Apartment, Long>, JpaSpecificationExecutor<Apartment> {

    @Query("select a from Apartment a where a.id = :id and a.hotel.id = :hotelId")
    Optional<Apartment> findByIdAndHotelId(@Param("id") Long id, @Param("hotelId") Long hotelId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Apartment> findById(@Param("id") Long id);
}
