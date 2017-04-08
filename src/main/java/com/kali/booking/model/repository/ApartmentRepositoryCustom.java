package com.kali.booking.model.repository;

import com.kali.booking.model.Apartment;
import com.kali.booking.model.ApartmentSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApartmentRepositoryCustom {

    Page<Apartment> getAvailableApartments(ApartmentSearchCriteria searchCriteria, Pageable pageable);
}
