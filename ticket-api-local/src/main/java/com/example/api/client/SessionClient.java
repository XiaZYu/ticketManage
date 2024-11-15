package com.example.api.client;

import com.example.api.po.Hall;
import com.example.api.po.Result;
import com.example.api.po.Seat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Component
@FeignClient("sessions-service")
public interface SessionClient {
    @GetMapping("/api/seats/getSeatById")
    Seat getSeatById(@RequestParam("seatId") String seatId);

    @PutMapping("/api/sessions/updateSeatJson")
    Result<String> updateSeatJson(@RequestParam("sessionId") String sessionId, @RequestBody List<String> seatIds);
}
