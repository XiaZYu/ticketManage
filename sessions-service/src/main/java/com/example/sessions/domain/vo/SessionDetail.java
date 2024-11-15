package com.example.sessions.domain.vo;

import lombok.Data;

@Data
public class SessionDetail {
    private String sessionId;

    private String filmId;

    private String filmName;

    private String hallId;

    private String hallName;

    private String time;

    private String seatJson;
}
