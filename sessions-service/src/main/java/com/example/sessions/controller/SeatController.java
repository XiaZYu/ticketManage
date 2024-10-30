package com.example.sessions.controller;

import com.example.sessions.domain.po.Hall;
import com.example.sessions.domain.po.Seat;
import com.example.sessions.domain.vo.Result;
import com.example.sessions.domain.vo.SeatsList;
import com.example.sessions.service.HallService;
import com.example.sessions.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "座位相关接口")
@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    private final HallService hallService;

    @Operation(summary = "添加座位")
    @PostMapping("/add")
    public Result<String> add(@RequestBody List<Seat> seat) {
        int result = 0;
        Hall hall = hallService.getHallById(seat.get(0).getHallId());

        if (hall == null) {
            return Result.error("不存在该影厅");
        }
        if (hall.getSeats() < seat.size()) {
            return Result.error("该影厅剩余座位不足");
        }

        for (Seat value : seat) {
            if (value.getAttr().isEmpty()){
                value.setAttr("普通座位");
            }

            value.setSeatId(UUID.randomUUID().toString());
            if (seatService.addSeat(value) == 1) {
                result = 1;
            } else {
                result = 0;
                break;
            }
        }

        if (result == 0) {
            return Result.error("添加座位失败");
        } else {
            return Result.success("添加座位成功");
        }
    }

    @Operation(summary = "修改座位")
    @PostMapping("/update")
    public Result<String> update(@RequestBody Seat seat) {
        Seat oldSeat = seatService.getSeatById(seat.getSeatId());
        if(seat.getAttr() == null){
            seat.setAttr(oldSeat.getAttr());
        }

        if (seatService.updateSeatStatus(seat) == 1) {
            return Result.success("修改座位成功");
        }
        return Result.error("修改座位失败");
    }

    @Operation(summary = "查询座位")
    @PostMapping("/list")
    public Result<SeatsList> list(
            @RequestParam String hallId,
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        SeatsList seatsList = new SeatsList();
        List<Seat> seats = seatService.getAvailableSeats(hallId, current, pageSize);
        seatsList.setCount(seats.size());
        seatsList.setList(seats);
        seatsList.setSize(pageSize);
        seatsList.setPage(current);
        return Result.success(seatsList);
    }

    @Operation(summary = "获取场次座位信息")
    @GetMapping("/sessionForSeat")
    public Result<List<Seat>> getSessionForSeat(
            @RequestParam(required = false) String sessionId,
            @RequestParam String hallId
    ) {
        List<Seat> seats = seatService.getAllSeats(hallId);
        System.out.println(seats);

        return Result.success(seats);
    }
}
