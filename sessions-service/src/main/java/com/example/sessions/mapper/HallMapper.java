package com.example.sessions.mapper;

import com.example.sessions.domain.po.Hall;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HallMapper {

    @Select("SELECT COUNT(*) FROM hall")
    int getHallsCount();

    List<Hall> queryHalls(@Param("hallName") String hallName, @Param("hallDesc") String hallDesc, @Param("current") int current, @Param("pageSize") int pageSize);

    @Select("SELECT * from hall WHERE hall_id = #{hallId}")
    Hall getHallById(String hallId);

    @Insert("insert into hall(hall_id, hall_name, hall_desc) " +
            "values(#{hallId}, #{hallName}, #{hallDesc})")
    int addHall(Hall hall);

    @Update("update hall set hall_name = #{hallName}, hall_desc = #{hallDesc} " +
            "where hall_id = #{hallId}")
    int updateHall(Hall hall);

    @Delete("delete from hall where hall_id = #{hallId}")
    void deleteHall(String hallId);

    @Update("update hall set seat_json_id = #{seatJsonId}, seats = #{seats} where hall_id = #{hallId}")
    int editSeatMap(String hallId, String seatJsonId, int seats);

    @Select("SELECT hall_name FROM hall")
    List<String> getHallForName();
}
