package com.kali.booking.model.repository;

import com.kali.booking.model.Room;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
}
