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
    public List<Seat> getAvailableSeats(String hallId, int pageNumber, int pageSize) {
        int size =( pageNumber - 1) * pageSize;
        return seatMapper.getSeats(hallId, size, pageSize);
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
}
