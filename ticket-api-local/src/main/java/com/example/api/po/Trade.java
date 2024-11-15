package com.example.api.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Trade implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String tradeId;

    private String filmId;

    private String hallId;

    private String sessionId;

    private String seat;

    private String uid;

    private Long phone;

    private String tradeDate;

    private String status;

    private Double price;
}
