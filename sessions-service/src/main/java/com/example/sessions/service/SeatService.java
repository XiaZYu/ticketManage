package com.example.sessions.service;

import com.example.sessions.domain.po.Seat;

import java.util.List;

public interface SeatService {

    int getSeatCount();

    List<Seat> getAvailableSeats(String hallId, int current, int pageSize);

    Seat getSeatById(String seatId);

    int updateSeatStatus(Seat seat);

    int addSeat(Seat seat);

    List<Seat> getAllSeats(String hallId);

    String getSeatJsonById(String seatJsonId);

    int addSeatJson(String seatJsonId, String seatJson);
}
