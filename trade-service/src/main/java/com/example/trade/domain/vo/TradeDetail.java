package com.example.trade.domain.vo;

import lombok.Data;

@Data
public class TradeDetail {
    private String tradeId;

    private String uid;

    private String filmId;

    private String hallId;

    private String sessionId;

    private String seat;

    private String filmName;

    private String posters;

    private String hallName;

    private String sRow;

    private String sColumn;

    private String attr;

    private String name;

    private Long phone;

    private String tradeDate;

    private String status;

    private Double price;
}
