package com.example.sessions.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Hall implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String hallId;

    private Integer seats;

    private String hallName;

    private String hallDesc;

    private String seatJsonId;
}
