package com.example.sessions.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class SessionsList {
    private Integer count;
    private Integer page;
    private Integer size;
    private List<SessionDetail> list;
}

