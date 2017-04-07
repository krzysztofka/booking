package com.kali.booking.controller;

import com.kali.booking.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class HotelController {

    public void registerHotel() {

    }

    public void registerRoom() {

    }

    public Page<Room> availableRooms() {
        return new PageImpl<Room>(new ArrayList<>());
    }
}
