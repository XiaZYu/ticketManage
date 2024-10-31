package com.example.api.client;

import com.example.api.po.Film;
import com.example.api.po.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "film-service")
public interface FilmClient {

    @GetMapping("/api/films/findById?filmId={filmId}")
    Result<Film> getFilmList(@PathVariable String filmId);

    @GetMapping("/api/films/getFilmByName")
    List<Film> getFilmByName(@RequestParam("filmName") String filmName);
}
