package com.example.film.service.impl;

import com.example.film.domain.po.Film;
import com.example.film.mapper.FilmMapper;
import com.example.film.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmMapper filmMapper;

    @Override
    public int countFilm() {
        return filmMapper.countFilm();
    }

    @Override
    public int addFilm(Film film) {
        UUID uuid = randomUUID();
        film.setFilmId(uuid.toString());
        return filmMapper.addFilm(film);
    }


    @Override
    public List<Film> getFilmList(String filmName, String filmType, int current, int pageSize) {
        current = (current - 1) * pageSize;
        return filmMapper.getFilmList(filmName, filmType, current, pageSize);
    }

    @Override
    public int updateFilm(Film film) {
        return filmMapper.updateFilm(film);
    }

    @Override
    public void deleteFilm(String filmId) {
        filmMapper.deleteById(filmId);
    }

    @Override
    public List<Film> getFilmByName(String filmName) {
        return filmMapper.getFilmByName(filmName);
    }

    @Override
    public List<String> getFilmForName() {
        return filmMapper.getFilmForName();
    }

    @Override
    public Film getFilmById(String id) {
        return filmMapper.getFilmById(id);
    }
}
