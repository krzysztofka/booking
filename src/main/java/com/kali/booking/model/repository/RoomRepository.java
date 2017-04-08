package com.kali.booking.model.repository;

import com.kali.booking.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {

    @Query("select r from Room r where r.id = :id and r.hotel.id = :hotelId")
    Optional<Room> findByIdAndHotelId(@Param("id") Long id, @Param("hotelId") Long hotelId);
}
