package com.kali.booking.model.repository;

import com.google.common.collect.Lists;
import com.kali.booking.model.Apartment;
import com.kali.booking.model.ApartmentSearchCriteria;
import com.kali.booking.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class ApartmentRepositoryImpl implements ApartmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Apartment> getAvailableApartments(ApartmentSearchCriteria searchCriteria, Pageable pageable) {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Apartment> criteriaQuery = criteriaBuilder.createQuery(Apartment.class);

        Root<Apartment> from = criteriaQuery.from(Apartment.class);

        fillApartmentSearchCriteria(criteriaQuery, criteriaBuilder, from, searchCriteria);

        TypedQuery<Apartment> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Apartment> apartments = typedQuery.getResultList();

        Long count = getAvailableApartmentsCount(criteriaBuilder, searchCriteria);

        return new PageImpl<>(apartments, pageable, count);
    }

    private Long getAvailableApartmentsCount(CriteriaBuilder criteriaBuilder, ApartmentSearchCriteria searchCriteria) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Apartment> from = criteriaQuery.from(Apartment.class);
        fillApartmentSearchCriteria(criteriaQuery, criteriaBuilder, from, searchCriteria);
        criteriaQuery.select(criteriaBuilder.count(from));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private void fillApartmentSearchCriteria(
                                             CriteriaQuery<?> criteriaQuery,
                                             CriteriaBuilder criteriaBuilder,
                                             Root<Apartment> apartment,
                                             ApartmentSearchCriteria searchCriteria) {
        List<Predicate> predicates = Lists.newArrayList();
        if (searchCriteria.getCity() != null) {
            predicates.add(addCityCriteria(searchCriteria.getCity(), apartment, criteriaBuilder));
        }

        if (searchCriteria.getPriceFrom() != null) {
            predicates.add(addPriceFromCriteria(searchCriteria.getPriceFrom(), apartment, criteriaBuilder));
        }

        if (searchCriteria.getPriceTo() != null) {
            predicates.add(addPriceToCriteria(searchCriteria.getPriceTo(), apartment, criteriaBuilder));
        }

        if (searchCriteria.getFrom() != null || searchCriteria.getTo() != null) {
            addBookingTimeCriteria(searchCriteria.getFrom(), searchCriteria.getTo(), apartment, criteriaQuery, criteriaBuilder);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
    }

    private Predicate addCityCriteria(String city, Root<Apartment> apartment, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(apartment.get("hotel").get("city"), city);
    }

    private Predicate addPriceFromCriteria(Long from, Root<Apartment> apartment, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.ge(apartment.get("dailyPrice"), from);
    }

    private Predicate addPriceToCriteria(Long to, Root<Apartment> apartment, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lt(apartment.get("dailyPrice"), to);
    }

    private Predicate addBookingTimeCriteria(Date from, Date to, Root<Apartment> apartment, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Subquery<Long> bookingSubQuery = criteriaQuery.subquery(Long.class);
        Root<Booking> booking = bookingSubQuery.from(Booking.class);
        bookingSubQuery.select(booking.get("apartment").get("id"));
        //criteriaBuilder.isNotMember();

        return apartment.get("id").in().not();
    }
}
