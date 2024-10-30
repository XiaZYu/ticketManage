package com.example.trade.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class TradeList {
    private Integer count;
    private Integer page;
    private Integer size;
    private List<TradeDetail> list;
}
