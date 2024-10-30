package com.example.trade.service;

import com.example.trade.domain.po.Trade;
import com.example.trade.domain.vo.TradeDetail;

import java.util.List;

public interface TradeService {

    int updateTrade(Trade trade);

    int addTrade(Trade trade);

    List<TradeDetail> getTradeList(String uid, String filmId, String hallId, int pageNumber, int pageSize);

    int countTrade();

    List<TradeDetail> getMyTradeList(String uid, int current, int pageSize);
}
