package com.kali.booking.repository;

import com.kali.booking.AbstractSpringIT;
import com.kali.booking.model.Apartment;
import com.kali.booking.model.repository.ApartmentRepository;
import com.kali.booking.model.repository.ApartmentSpecifications;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@Sql({"/sql/clean-up.sql", "/sql/hotel-test-data.sql"})
public class ApartmentRepositoryIT extends AbstractSpringIT {

    @Autowired
    private ApartmentRepository repository;

    @Test
    public void shouldReturnAvailableApartmentsByCity() {
        // given
        String cityName = "Warsaw";

        // when
        List<Apartment> apartmentList = repository.findAll(ApartmentSpecifications.apartmentInCity(cityName));

        // then
        assertEquals(4, apartmentList.size());
        apartmentList.forEach(apartment -> assertEquals(cityName, apartment.getHotel().getCity()));
    }

    @Test
    public void shouldReturnAvailableApartmentsWithGivenPriceRange() {
        // given
        Long priceFrom = 10000L;
        Long priceTo = 20000L;

        // when
        List<Apartment> apartmentList = repository.findAll(Specifications.
                        where(ApartmentSpecifications.priceFrom(priceFrom))
                        .and(ApartmentSpecifications.priceTo(priceTo)));

        // then
        assertEquals(3, apartmentList.size());
        apartmentList.forEach(apartment ->
            assertTrue(apartment.getDailyPrice() >= priceFrom && apartment.getDailyPrice() < priceTo));
    }

    @Test
    public void shouldReturnAvailableApartmentsForGivenDateRange() {
        // given
        Date from = toDate(2160, 6, 3);
        Date to = toDate(2160, 6, 12);

        // when
        List<Apartment> apartmentList = repository.findAll(ApartmentSpecifications.bookingTime(from, to));

        // then
        assertEquals(1, apartmentList.size());
        assertEquals(Long.valueOf(-11), apartmentList.get(0).getId());
    }
}
