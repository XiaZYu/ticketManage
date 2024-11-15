package com.example.pay.service.impl;

import com.example.pay.domain.po.Payment;
import com.example.pay.mapper.PayMapper;
import com.example.pay.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    private final PayMapper payMapper;


    @Override
    public void createPayment(Payment payment) {
        payMapper.createPayment(payment);
    }
}
