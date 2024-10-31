package com.example.sessions.mapper;

import com.example.sessions.domain.vo.SessionDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SessionMapper {
    List<SessionDetail> getSessionsByFilmId(@Param("filmId") String filmId, @Param("current") int current, @Param("pageSize") int pageSize);

    @Select("select count(*) from session")
    Integer Count();

    @Select("select hall_id from session where time = #{time}")
    List<String> getHallByTime(String time);

    @Insert("Insert into session(hall_id, film_id, time, session_id) values(#{hallId}, #{filmId}, #{time}, #{sessionId})")
    int addSession(SessionDetail session);

    @Delete("delete from session where session_id = #{id}")
    int deleteSession(String id);

    @Update("update session set hall_id = #{hallId}, film_id = #{filmId}, time = #{time}, session_id = #{sessionId} where session_id = #{sessionId}")
    int updateSession(SessionDetail session);

    @Select("select hall_id from session where session_id = #{sessionId}")
    String getHallById(String hallId);
}
