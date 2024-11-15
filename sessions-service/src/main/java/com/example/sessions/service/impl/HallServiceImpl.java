package com.example.sessions.service.impl;

import com.example.sessions.domain.po.Hall;
import com.example.sessions.mapper.HallMapper;
import com.example.sessions.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallMapper hallMapper;

    @Override
    public int getHallsCount() {
        return hallMapper.getHallsCount();
    }

    @Override
    public List<Hall> queryHalls(String hallName, String hallDesc,int current, int pageSize) {
        current =( current - 1) * pageSize;
        return hallMapper.queryHalls(hallName, hallDesc,current, pageSize);
    }

    @Override
    public Hall getHallById(String hallId) {
        return hallMapper.getHallById(hallId);
    }

    @Override
    public int addHall(Hall hall) {
        return hallMapper.addHall(hall);
    }

    @Override
    public int updateHall(Hall hall) {
        return hallMapper.updateHall(hall);
    }

    @Override
    public void deleteHall(String hallId) {
        hallMapper.deleteHall(hallId);
    }

    @Override
    public int editSeatMap(String hallId, String seatJsonId, Integer seats) {
        return hallMapper.editSeatMap(hallId, seatJsonId, seats);
    }

    @Override
    public List<String> getHallForName() {
        return hallMapper.getHallForName();
    }
}
