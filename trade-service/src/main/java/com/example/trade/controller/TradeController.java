package com.example.trade.controller;

import com.example.api.client.UserClient;
import com.example.api.client.FilmClient;
import com.example.api.po.Film;
import com.example.trade.domain.po.Trade;
import com.example.trade.domain.vo.TradeDetail;
import com.example.trade.domain.vo.Result;
import com.example.trade.domain.vo.TradeList;
import com.example.trade.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tag(name = "交易相关接口")
@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    private  FilmClient filmClient;

    @Autowired
    private  UserClient userClient;



    @Operation(summary = "创建交易")
    @PostMapping("/create")
    public Result<String> createTrade(@RequestBody Trade trade) {
        //设置tradeId
        trade.setTradeId(UUID.randomUUID().toString());

        //设置uid
        trade.setUid(getInfo(userClient.getCurrentUser().getData().toString(), "uid=(.*?),"));

        //更改座位状态
//        Seat seat = new Seat();
//        seat.setHallId(trade.getHallId());
//        seat.setSeatId(trade.getSeat());
//        switch (trade.getStatus()) {
//            case "已支付" -> seat.setStatus(1);
//            case "已取消" -> seat.setStatus(0);
//            case "未支付" -> seat.setStatus(2);
//        }
//        sessionClient.updateSeats(seat);

        if (tradeService.addTrade(trade) == 1) {
            return Result.success("创建成功");
        }
        return Result.success("创建失败");
    }

    @Operation(summary = "获取交易列表")
    @GetMapping("/list")
    public Result<TradeList> getTrades(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String filmName,
            @RequestParam(required = false) Long phone,
            @RequestParam(required = false) String hallId,
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        TradeList tradeList = new TradeList();
        List<TradeDetail> tradeDetailList = new ArrayList<>();
        List<Film> films;
        String uid = null;
        String filmId = null;
        if (name != null && !name.isEmpty()){
            System.out.println(userClient.getUserByName(name));
            uid = getInfo(userClient.getUserByName(name).getData().toString(),"uid=(.*?),");
       }
        if (phone != null){
            uid = getInfo(userClient.getUserByPhone(phone).getData().toString(),"uid=(.*?),");
        }
        if (filmName != null && !filmName.isEmpty()){
            films = filmClient.getFilmByName(filmName);

            if (films.size() == 1){
                tradeDetailList.addAll(tradeService.getTradeList(uid, films.get(0).getFilmId(), hallId, current, pageSize));
            }else{
                for (Film film : films){
                    tradeDetailList.addAll(tradeService.getTradeList(uid, film.getFilmId(), hallId, current, pageSize));
                }
            }
        }
        if (tradeDetailList.isEmpty()){
            tradeDetailList.addAll(tradeService.getTradeList(uid, filmId, hallId, current, pageSize));
        }
        tradeList.setSize(pageSize);
        tradeList.setPage(current);
        tradeList.setCount(tradeService.countTrade());
        tradeList.setList(tradeDetailList);
        return Result.success(tradeList);
    }

    @Operation(summary = "获取个人交易列表")
    @GetMapping("/my-list")
    public Result<TradeList> getMyTrades(
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        String uid = getInfo(userClient.getCurrentUser().getData().toString(), "uid=(.*?),");
        TradeList tradeList = new TradeList();
        List<TradeDetail> tradeDetailList = tradeService.getMyTradeList(uid, current, pageSize);

        tradeList.setSize(pageSize);
        tradeList.setPage(current);
        tradeList.setCount(tradeDetailList.size());
        tradeList.setList(tradeDetailList);
        return Result.success(tradeList);
    }




    @Operation(summary = "更新交易详情")
    @PutMapping("/update")
    public Result<String> updateTrade(@RequestBody Trade trade) {
        if (tradeService.updateTrade(trade) == 1) {
            return Result.success("更新成功");
        }
        return Result.success("更新失败");
    }

    public static String getInfo(String str, String UserPattern) {
        Pattern pattern = Pattern.compile(UserPattern);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}

