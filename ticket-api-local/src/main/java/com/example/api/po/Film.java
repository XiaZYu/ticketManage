package com.example.api.po;

import lombok.Data;


@Data
public class Film{


    private String filmId;

    private String filmName;

    private String filmType;

    private String language;

    private Integer filmDuration;

    private String synopsis;

    private Double price;

    private String posters;

    private Integer boxOffice;

}
