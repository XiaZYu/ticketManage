package com.example.sessions.domain.vo;

import com.example.sessions.domain.po.Seat;
import lombok.Data;

import java.util.List;

@Data
public class SeatsList {
    private Integer count;
    private Integer page;
    private Integer size;
    private List<Seat> list;
}
