package com.example.film.domain.vo;


import com.example.film.domain.po.Film;
import lombok.Data;

import java.util.List;

@Data
public class FilmList {
    private Integer count;
    private Integer page;
    private Integer size;
    private List<Film> list;
}
