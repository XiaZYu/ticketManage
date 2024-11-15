package com.example.film.service;



import com.example.film.domain.po.Film;

import java.util.List;

public interface FilmService {
    int countFilm();

    int addFilm(Film film);

    List<Film> getFilmList(String filmName, String filmType, int current, int pageSize);

    int updateFilm(Film film);

    void deleteFilm(String filmId);

    List<Film> getFilmByName(String filmName);

    List<String> getFilmForName();

    Film getFilmById(String id);
}
