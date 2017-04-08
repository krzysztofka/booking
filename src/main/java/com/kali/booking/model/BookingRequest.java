package com.kali.booking.model;

import java.util.Date;

public class BookingRequest {

    private Long hotelId;

    private Long userId;

    private Long roomId;

    private Date from;

    private Date to;

    public BookingRequest() {
    }

    public BookingRequest(Long hotelId, Long userId, Long roomId, Date from, Date to) {
        this.hotelId = hotelId;
        this.userId = userId;
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
}
