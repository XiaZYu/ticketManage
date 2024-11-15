package com.example.pay.mapper;

import com.example.pay.domain.po.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayMapper {

    @Insert("INSERT INTO payment (payment_Id, uid, trade_id, payment_time, payment_status, payment_method, price) " +
            "VALUES (#{paymentId}, #{uid}, #{tradeId}, NOW(), #{paymentStatus}, #{paymentMethod}, #{price})")
    void createPayment(Payment payment);
}
