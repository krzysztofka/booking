package com.kali.booking.model;

import javax.persistence.*;

@Entity
@Table(
    name="room",
    uniqueConstraints=
        @UniqueConstraint(columnNames={"hotel_id", "name"})
)
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @Column(name = "daily_price")
    private Long dailyPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Long getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(Long dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}
