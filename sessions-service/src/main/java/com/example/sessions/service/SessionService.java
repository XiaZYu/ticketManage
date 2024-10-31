package com.example.sessions.service;

import com.example.sessions.domain.vo.SessionDetail;

import java.util.List;

public interface SessionService {
    List<SessionDetail> getSessionsByFilmId(String filmId, int current, int pageSize);

    Integer Count();

    List<String> getHallByTime(String time);

    int addSession(SessionDetail session);

    int deleteSession(String id);

    int updateSession(SessionDetail session);

    String getHallById(String hallId);
}
