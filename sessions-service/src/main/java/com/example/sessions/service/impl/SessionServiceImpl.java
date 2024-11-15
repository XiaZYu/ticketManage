package com.example.sessions.service.impl;

import com.example.sessions.domain.po.Session;
import com.example.sessions.domain.vo.SessionDetail;
import com.example.sessions.mapper.SessionMapper;
import com.example.sessions.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionMapper sessionMapper;

    @Override
    public List<SessionDetail> getSessionsByFilmId(String filmId, String time) {
        return sessionMapper.getSessionsByFilmId(filmId, time);
    }

    @Override
    public Integer Count() {
        return sessionMapper.Count();
    }

    @Override
    public List<String> getHallByTime(String time) {
        return sessionMapper.getHallByTime(time);
    }

    @Override
    public int addSession(SessionDetail session) {
        return sessionMapper.addSession(session);
    }

    @Override
    public int deleteSession(String id) {
        return sessionMapper.deleteSession(id);
    }

    @Override
    public int updateSession(SessionDetail session) {
        return sessionMapper.updateSession(session);
    }

    @Override
    public String getHallById(String hallId) {
        return sessionMapper.getHallById(hallId);
    }

    @Override
    public Session getSeatMap(String sessionId) {
        return sessionMapper.getSeatMap(sessionId);
    }

    @Override
    public List<SessionDetail> getSessions(String filmId, String time, Integer current, Integer pageSize) {
        current = (current - 1) * pageSize;
        return sessionMapper.getSessions(filmId, time, current, pageSize);
    }

    @Override
    public int updateSeatJson(String sessionId, String seatJson) {
        return sessionMapper.updateSeatJson(sessionId, seatJson);
    }
}
