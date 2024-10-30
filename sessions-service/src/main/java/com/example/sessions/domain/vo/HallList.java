package com.example.sessions.domain.vo;

import com.example.sessions.domain.po.Hall;
import lombok.Data;

import java.util.List;

@Data
public class HallList {
    private Integer count;
    private Integer page;
    private Integer size;
    private List<Hall> list;
}
