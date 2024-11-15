package com.example.trade.domain.vo;

import lombok.Data;

@Data
public class SeatList {
    private String seat;
    private Integer sRow;
    private Integer sColumn;
    private String seatType;
}
