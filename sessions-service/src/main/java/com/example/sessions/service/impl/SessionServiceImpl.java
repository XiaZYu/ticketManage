package com.example.sessions.service.impl;

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
    public List<SessionDetail> getSessionsByFilmId(String filmId, int current, int pageSize) {
         current = ( current - 1) * pageSize;
        return sessionMapper.getSessionsByFilmId(filmId, current, pageSize);
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
}
