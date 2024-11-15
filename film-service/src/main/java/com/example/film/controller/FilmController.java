package com.example.film.controller;

import com.example.film.domain.po.Film;
import com.example.film.domain.vo.FilmList;
import com.example.film.domain.vo.Result;
import com.example.film.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "电影管理相关接口")
@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @Operation(summary = "获取电影列表")
    @GetMapping("/list")
    public Result<FilmList> getFilmList(
            @RequestParam(required = false) String filmName,
            @RequestParam(required = false) String filmType,
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        FilmList filmList = new FilmList();
        List<Film> film = filmService.getFilmList(filmName, filmType, current, pageSize);
        if ((filmName != null && !filmName.isEmpty()) || (filmType != null && !filmType.isEmpty())){
            filmList.setCount(film.size());
        }else{
            filmList.setCount(filmService.countFilm());
        }
        filmList.setPage(current);
        filmList.setSize(pageSize);
        filmList.setList(film);
        return Result.success(filmList);
    }

    @Operation(summary = "添加电影")
    @PostMapping("/add")
    public Result<String> addFilm(@RequestBody Film film) {
        if (!filmService.getFilmList(film.getFilmName(), film.getFilmType(), 1, 10).isEmpty()) {
            return Result.error("电影已存在");
        }
        film.setFilmId(UUID.randomUUID().toString());
        if (filmService.addFilm(film) == 0) {
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @Operation(summary = "删除电影")
    @DeleteMapping("/delete")
    public Result<String> deleteFilm(@RequestParam String id) {
        filmService.deleteFilm(id);
        return Result.success("删除成功");
    }

    @Operation(summary = "更新电影")
    @PutMapping("/update")
    public Result<String> updateFilm(@RequestBody Film film) {
        if (filmService.updateFilm(film) == 0) {
            return Result.error("更新失败");
        }
        return Result.success("更新成功");
    }

    @Operation(summary = "获取电影详情")
    @GetMapping("/getFilmById")
    public Result<Film> getFilmById(@RequestParam String id) {
        Film film = filmService.getFilmById(id);
        if (film == null) {
            return Result.error("电影不存在");
        }
        return Result.success(film);
    }

    @GetMapping("/getFilmByName")
    public List<Film>   getFilmByName(@RequestParam String filmName) {
        return filmService.getFilmByName(filmName);
    }

    @GetMapping("/getFilmForName")
    public List<String>  getFilmForName() {
        return filmService.getFilmForName();
    }
}
