package com.example.film.mapper;

import com.example.film.domain.po.Film;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface FilmMapper {


    @Update("UPDATE film SET film_name = #{filmName}," +
            "film_type = #{filmType}, language = #{language}, film_duration = #{filmDuration}," +
            "synopsis = #{synopsis}, price = #{price}, posters = #{posters}, box_office = #{boxOffice} WHERE film_id = #{filmId}")
    int updateFilm(Film film);

    List<Film> getFilmList(String filmName, String filmType, int pageNumber, int pageSize);

    @Delete("DELETE FROM film WHERE film_id = #{filmId}")
    void deleteById(String filmId);


    @Insert("INSERT INTO film(film_id,film_name,film_type, language, film_duration, synopsis, price, posters, box_office) " +
            "VALUES(#{filmId},#{filmName}, #{filmType}, #{language}, #{filmDuration}, #{synopsis}, #{price}, #{posters}, #{boxOffice})")
    int addFilm(Film film);


    @Select("SELECT COUNT(*) FROM film")
    int countFilm();

    @Select("SELECT * FROM film WHERE film_name like concat('%',#{filmName},'%')")
    List<Film> getFilmByName(String filmName);

    @Select("SELECT film_name FROM film")
    List<String> getFilmForName();
}
