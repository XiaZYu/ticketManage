package com.example.trade.domain.dto;

import com.example.trade.domain.vo.SeatList;
import lombok.Data;

import java.util.List;


@Data
public class TradeDto {
    private String tradeId;

    private String uid;

    private String filmId;

    private String hallId;

    private String sessionId;

    private String filmName;

    private String posters;

    private String hallName;

    private List<SeatList> seatList;

    private String time;

    private String name;

    private Long phone;

    private String tradeDate;

    private String status;

    private Double price;
}
