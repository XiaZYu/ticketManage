package com.example.pay.controller;

import com.example.api.client.SessionClient;
import com.example.api.client.TradeClient;
import com.example.api.client.UserClient;
import com.example.pay.domain.po.Payment;
import com.example.pay.domain.vo.Result;
import com.example.pay.service.PayService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tag(name = "支付相关接口")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PayController {
    private final PayService payService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private TradeClient tradeClient;

    @Autowired
    private SessionClient sessionClient;

    // 支付接口
    @Operation(summary = "支付接口")
    @RequestMapping("/pay")
    @GlobalTransactional(rollbackFor = Exception.class)
    public Result<String> Pay(
            @RequestParam String tradeId,
            @RequestParam(required = false) String uid,
            @RequestParam Double price) {
        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID().toString());
        if (uid.isEmpty()) {
            uid = getInfo(userClient.getCurrentUser().toString(), "uid=(.*?),");
        }

        try {
            if (!Objects.equals(uid, tradeClient.getTrade(tradeId).getUid())) {
                throw new RuntimeException("用户不匹配");
            }

            // 扣减余额
            if (userClient.reduceBalance(uid, price).getCode() == 200) {
                System.out.println("扣减余额成功");
            } else {
                throw new RuntimeException(userClient.reduceBalance(uid, price).getMessage());
            }
            // 更新订单状态
            if (tradeClient.updateTradeStatus(tradeId, "已支付").getCode() == 200) {
                System.out.println("更新订单状态成功");
            } else {
                throw new RuntimeException(tradeClient.updateTradeStatus(tradeId, "已支付").getMessage());
            }
            // 更新座位信息
            String sessionId = getInfo(tradeClient.getTrade(tradeId).toString(), "sessionId=(.*?),");
            List<String> seatIds = new ArrayList<>();
            seatIds.addAll(Arrays.asList(getInfo(tradeClient.getTrade(tradeId).toString(), "seat=(.*?),").split("//")));
            if (sessionClient.updateSeatJson(sessionId, seatIds).getCode() == 200) {
                System.out.println("更新座位信息成功");
            } else {
                throw new RuntimeException(sessionClient.updateSeatJson(sessionId, seatIds).getMessage());
            }
            //创建支付记录
            payment.setTradeId(tradeId);
            payment.setUid(uid);
            payment.setPrice(price);
            payment.setPaymentStatus("已支付");
            payment.setPaymentMethod("余额");
            payService.createPayment(payment);
            return Result.success("支付成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
