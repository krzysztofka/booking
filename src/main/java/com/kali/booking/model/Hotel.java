package com.kali.booking.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(
        name="hotel",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "city"})
)
public class Hotel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 50)
    @NotBlank
    private String name;

    @Size(min = 2, max = 50)
    @NotBlank
    private String city;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
