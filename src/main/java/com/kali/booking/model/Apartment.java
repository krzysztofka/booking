package com.kali.booking.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
    name="apartment",
    uniqueConstraints=
        @UniqueConstraint(columnNames={"hotel_id", "name"})
)
public class Apartment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 30)
    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="hotel_id", nullable = false)
    private Hotel hotel;

    @Min(0)
    @NotNull
    @Column(name = "daily_price", nullable = false)
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
