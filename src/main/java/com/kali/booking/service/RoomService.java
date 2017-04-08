package com.kali.booking.service;

import com.kali.booking.exceptions.DataConflictException;
import com.kali.booking.exceptions.EntityNotFoundException;
import com.kali.booking.model.Hotel;
import com.kali.booking.model.Room;
import com.kali.booking.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoom(Long roomId, Long hotelId) {
        Optional<Room> room = roomRepository.findByIdAndHotelId(roomId, hotelId);
        return room.orElseThrow(EntityNotFoundException::new);
    }

    public Room registerRoom(Hotel hotel, Room room) {
         room.setHotel(hotel);
         try {
             return roomRepository.save(room);
         } catch (DataIntegrityViolationException ex) {
             throw new DataConflictException("Hotel room with given name already exists");
         }
    }
}
