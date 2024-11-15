package com.example.pay.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Payment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String paymentId;

    private String tradeId;

    private String uid;

    private String paymentTime;

    private String paymentMethod;

    private String paymentStatus;

    private Double price;
}
