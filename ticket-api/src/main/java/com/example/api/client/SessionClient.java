package com.example.api.client;

import com.example.api.po.Hall;
import com.example.api.po.Result;
import com.example.api.po.Seat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("sessions-service")
public interface SessionClient {

    @PostMapping("/api/seats/update")
    Result<Seat> updateSeats(Seat seat);

    @GetMapping("/api/hall/queryHallById?hallId={hallId}")
    Result<Hall> queryHallById(@PathVariable("hallId") String hallId);

    @GetMapping("/api/seats/getSeatById?seatId={seatId}")
    Result<Seat> getSeatById(@PathVariable("seatId") String seatId);
}
