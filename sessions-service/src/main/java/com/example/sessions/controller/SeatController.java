package com.example.sessions.controller;

import com.example.sessions.domain.po.Seat;
import com.example.sessions.domain.vo.*;
import com.example.sessions.service.SeatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "座位相关接口")
@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;


    @Operation(summary = "添加座位")
    @PostMapping("/add")
    public Result<String> add(@RequestBody String seatJson) {
        Seat seat = new Seat();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SeatJsonDetail[] seatJsonDetails = objectMapper.readValue(seatJson, SeatJsonDetail[].class);
            for (SeatJsonDetail seatJsonDetail : seatJsonDetails) {
                for (int i = 1; i <= seatJsonDetail.getSeats().size(); i++) {
                    seat.setSeatJsonId(seatJsonDetail.getId());
                    seat.setSeatId(seatJsonDetail.getSeats().get(i - 1).getId());
                    seat.setSRow(seatJsonDetail.getSeats().get(i - 1).getRow());
                    seat.setSColumn(seatJsonDetail.getSeats().get(i - 1).getCol());
                    seat.setSeatType(seatJsonDetail.getSeats().get(i - 1).getSeatType());
                    seat.setSeatType(seatJsonDetail.getSeats().get(i - 1).getSeatType());
                    seat.setStatus(seatJsonDetail.getSeats().get(i - 1).getStatus());
                    seatService.addSeat(seat);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Result.success("添加座位成功");
    }

    @Operation(summary = "获取座位图id获取座位信息")
    @GetMapping("/get")
    public Result<String> getSeatJson(
            @RequestParam String seatJsonId
    ) {
        return Result.success(seatService.getSeatJsonById(seatJsonId));
    }

    @Operation(summary = "添加座位图")
    @PostMapping("/addSeatJson")
    public Result<String> addSeatJson(
            @RequestBody String seatJson
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SeatJsonDetail[] seatJsonDetails = objectMapper.readValue(seatJson, SeatJsonDetail[].class);
        String seatJsonId = "";
        for (SeatJsonDetail seatJsonDetail : seatJsonDetails) {
            seatJsonId = seatJsonDetail.getId();
        }
        if (!seatService.getSeatJsonById(seatJsonId).isEmpty()){
            return Result.error("座位图已存在");
        }
        if (seatService.addSeatJson(seatJsonId, seatJson) == 1){
            return Result.success("添加座位图成功");
        }
        return Result.error("添加座位图失败");
    }

    @GetMapping("/getSeatById")
    Seat getSeatById(@RequestParam("seatId") String seatId){
        return seatService.getSeatById(seatId);
    }
}
