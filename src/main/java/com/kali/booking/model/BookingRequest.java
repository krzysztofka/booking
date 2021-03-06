package com.kali.booking.model;

import com.kali.booking.model.validations.DateRange;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@DateRange(from = "from", to = "to")
public class BookingRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long apartmentId;

    @Future
    @NotNull
    private Date from;

    @NotNull
    private Date to;

    public BookingRequest() {
    }

    public BookingRequest(Long userId, Long apartmentId, Date from, Date to) {
        this.userId = userId;
        this.apartmentId = apartmentId;
        this.from = from;
        this.to = to;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "userId=" + userId +
                ", apartmentId=" + apartmentId +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
