package com.example.sessions.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Seat implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String seatId;

    private String seatJsonId;

    private Integer sRow;

    private Integer sColumn;

    private String seatType;

    private String status;
}
