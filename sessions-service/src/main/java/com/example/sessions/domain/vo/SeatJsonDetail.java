package com.example.sessions.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class SeatJsonDetail {
    private String id;
    private String name;
    private String x;
    private String y;
    private String width;
    private String height;
    private String type;
    private String rows;
    private String cols;
    private List<Seats> seats;
    private String showName;

    @Data
    public static class Seats {
        private String id;
        private int x;
        private int y;
        private int width;
        private int height;
        private String type;
        private int _row;
        private int _col;
        private int row;
        private int col;
        private String status;
        private String seatType;
        private String areaId;
    }
}

