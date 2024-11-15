package com.example.api.client;

import com.example.api.po.Result;
import com.example.api.po.Trade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Component
@FeignClient("trade-service")
public interface TradeClient {
    @PutMapping("/api/trade/updateTradeStatus")
    Result<String> updateTradeStatus(@RequestParam("tradeId") String tradeId, @RequestParam("status") String status);

    @GetMapping("/api/trade/getTrade")
    Trade getTrade(@RequestParam("tradeId") String tradeId);
}
