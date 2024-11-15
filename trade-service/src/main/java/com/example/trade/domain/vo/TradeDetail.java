package com.example.trade.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class TradeDetail {
    private String tradeId;

    private String uid;

    private String filmId;

    private String hallId;

    private String sessionId;

    private String filmName;

    private String time;

    private String posters;

    private String hallName;

    private String seat;

    private List<SeatList> seatList;

    private String name;

    private Long phone;

    private String tradeDate;

    private String status;

    private Double price;
}
