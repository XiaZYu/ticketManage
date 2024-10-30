package com.example.trade.service.impl;

import com.example.trade.domain.po.Trade;
import com.example.trade.domain.vo.TradeDetail;
import com.example.trade.mapper.TradeMapper;
import com.example.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeMapper tradeMapper;

    @Override
    public int updateTrade(Trade trade) {
        return tradeMapper.updateTrade(trade);
    }

    @Override
    public int addTrade(Trade trade) {
        return tradeMapper.addTrade(trade);
    }

    @Override
    public List<TradeDetail> getTradeList(String uid, String filmId, String hallId, int pageNumber, int pageSize) {
        int size =( pageNumber - 1) * pageSize;
        return tradeMapper.getTradeList(uid, filmId, hallId, size, pageSize);
    }

    @Override
    public int countTrade() {
        return tradeMapper.countTrade();
    }

    @Override
    public List<TradeDetail> getMyTradeList(String uid, int current, int pageSize) {
        int size = (current - 1) * pageSize;
        return tradeMapper.getMyTradeList(uid, size, pageSize);
    }
}
