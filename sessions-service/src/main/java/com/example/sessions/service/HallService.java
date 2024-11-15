package com.example.sessions.service;

import com.example.sessions.domain.po.Hall;

import java.util.List;

public interface HallService {

    int getHallsCount();

    List<Hall> queryHalls(String hallName, String hallDesc,int current,int pageSize);

    Hall getHallById(String hallId);

    int addHall(Hall hall);

    int updateHall(Hall hall);

    void deleteHall(String hallId);

    int editSeatMap(String hallId, String seatJsonId, Integer seats);

    List<String> getHallForName();
}
