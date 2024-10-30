package com.example.trade.mapper;

import com.example.trade.domain.po.Trade;
import com.example.trade.domain.vo.TradeDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TradeMapper {

    @Update("UPDATE trade set status = #{status} WHERE trade_id = #{tradeId}")
    int updateTrade(Trade trade);

    @Insert("INSERT INTO trade(trade_id, film_id, session_id, hall_id, seat, uid, phone, price, trade_date, status)" +
            " VALUES(#{tradeId}, #{filmId}, #{sessionId}, #{hallId}, #{seat}, #{uid}, #{phone}, #{price}, NOW(), #{status})")
    int addTrade(Trade trade);

    List<TradeDetail> getTradeList(String uid, String filmId, String hallId, int pageNumber, int pageSize);

    @Select("SELECT COUNT(*) FROM trade")
    int countTrade();

    List<TradeDetail> getMyTradeList(String uid, int pageNumber, int pageSize);
}
