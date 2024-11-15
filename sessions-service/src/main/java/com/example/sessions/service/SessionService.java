package com.example.sessions.service;

import com.example.sessions.domain.po.Session;
import com.example.sessions.domain.vo.SessionDetail;

import java.util.Collection;
import java.util.List;

public interface SessionService {
    List<SessionDetail> getSessionsByFilmId(String filmId, String time);

    Integer Count();

    List<String> getHallByTime(String time);

    int addSession(SessionDetail session);

    int deleteSession(String id);

    int updateSession(SessionDetail session);

    String getHallById(String hallId);

    Session getSeatMap(String sessionId);

    List<SessionDetail> getSessions(String filmId, String time, Integer current, Integer pageSize);

    int updateSeatJson(String sessionId, String seatJson);
}
