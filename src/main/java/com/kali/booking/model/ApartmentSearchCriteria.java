package com.kali.booking.model;

import com.kali.booking.model.validations.DateRange;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@DateRange(from = "from", to = "to")
public class ApartmentSearchCriteria {

    @Size(min = 2, max = 50)
    private String city;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Future
    private Date from;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date to;

    @Min(0L)
    private Long priceFrom;

    @Min(0L)
    private Long priceTo;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Long getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Long priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Long getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Long priceTo) {
        this.priceTo = priceTo;
    }

    @Override
    public String toString() {
        return "ApartmentSearchCriteria{" +
                "city='" + city + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", priceFrom=" + priceFrom +
                ", priceTo=" + priceTo +
                '}';
    }
}
