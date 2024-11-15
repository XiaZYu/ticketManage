package com.example.sessions.mapper;

import com.example.sessions.domain.po.Session;
import com.example.sessions.domain.vo.SessionDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SessionMapper {
    List<SessionDetail> getSessionsByFilmId(@Param("filmId") String filmId, @Param("time") String time);

    @Select("select count(*) from session")
    Integer Count();

    @Select("select hall_id from session where time = #{time}")
    List<String> getHallByTime(String time);

    @Insert("Insert into session(hall_id, film_id, time, session_id, seat_json) values(#{hallId}, #{filmId}, #{time}, #{sessionId}, #{seatJson})")
    int addSession(SessionDetail session);

    @Delete("delete from session where session_id = #{id}")
    int deleteSession(String id);

    @Update("update session set hall_id = #{hallId}, film_id = #{filmId}, time = #{time} where session_id = #{sessionId}")
    int updateSession(SessionDetail session);

    @Select("select hall_id from session where session_id = #{sessionId}")
    String getHallById(String hallId);

    @Select("select * from session where session_id = #{sessionId}")
    Session getSeatMap(String sessionId);

    List<SessionDetail> getSessions(@Param("filmId") String filmId, @Param("time") String time, @Param("current") Integer current, @Param("pageSize") Integer pageSize);

    @Update("update session set seat_json = #{seatJson} where session_id = #{sessionId}")
    int updateSeatJson(String sessionId, String seatJson);
}
