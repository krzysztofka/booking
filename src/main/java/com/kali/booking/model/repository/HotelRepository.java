package com.kali.booking.model.repository;

import com.kali.booking.model.Hotel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
}
