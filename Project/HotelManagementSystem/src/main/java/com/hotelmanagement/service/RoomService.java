package com.hotelmanagement.service;

import com.hotelmanagement.dao.RoomDAO;
import com.hotelmanagement.dao.impl.RoomDAOImpl;
import com.hotelmanagement.entity.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RoomService {

    private RoomDAO roomDAO = new RoomDAOImpl();

    public void addRoom(Room room) {
        roomDAO.save(room);
    }

    public List<Room> getAvailableRooms() {
        return roomDAO.findAll().stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }
}
