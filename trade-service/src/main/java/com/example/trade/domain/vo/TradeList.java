package com.example.trade.domain.vo;

import com.example.trade.domain.dto.TradeDto;
import lombok.Data;

import java.util.List;

@Data
public class TradeList {
    private Integer count;
    private Integer page;
    private Integer size;
    private List<TradeDto> list;
}
