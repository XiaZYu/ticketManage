package com.example.sessions.service.impl;

import com.example.sessions.domain.po.Seat;
import com.example.sessions.mapper.SeatMapper;
import com.example.sessions.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatMapper seatMapper;

    @Override
    public int getSeatCount() {
        return seatMapper.getSeatCount();
    }

    @Override
    public List<Seat> getAvailableSeats(String hallId, int current, int pageSize) {
        current =( current - 1) * pageSize;
        return seatMapper.getSeats(hallId, current, pageSize);
    }

    @Override
    public Seat getSeatById(String seatId) {
        return seatMapper.getSeatById(seatId);
    }

    @Override
    public int updateSeatStatus(Seat seat) {
        return seatMapper.updateSeat(seat);
    }

    @Override
    public int addSeat(Seat seat) {
        return seatMapper.addSeat(seat);
    }

    @Override
    public List<Seat> getAllSeats(String hallId) {
        return seatMapper.getAllSeats(hallId);
    }

    @Override
    public String getSeatJsonById(String seatJsonId) {
        return seatMapper.getSeatJsonById(seatJsonId);
    }

    @Override
    public int addSeatJson(String seatJsonId, String seatJson) {
        return seatMapper.addSeatJson(seatJsonId, seatJson);
    }
}
