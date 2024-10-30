package com.example.sessions.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Session implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String sessionId;

    private String filmId;

    private String hallId;

    private String time;

    private String seatJson;
}
