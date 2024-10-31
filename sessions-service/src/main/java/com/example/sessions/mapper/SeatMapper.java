package com.example.sessions.mapper;

import com.example.sessions.domain.po.Seat;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeatMapper {

    @Select("SELECT * FROM seat WHERE hall_id = #{hallId} ORDER BY s_row, s_column LIMIT #{current}, #{pageSize}")
    List<Seat> getSeats(@Param("hallId") String hallId, @Param("current") int current, @Param("pageSize") int pageSize);

    @Update("UPDATE seat SET attr = #{attr}, status = #{status} WHERE seat_id = #{seatId}")
    int updateSeat(Seat seat);

    @Insert("INSERT INTO seat (seat_id, hall_id, s_row, s_column, attr, status)" +
            " VALUES (#{seatId}, #{hallId}, #{sRow}, #{sColumn}, #{attr}, #{status})")
    int addSeat(Seat seat);

    @Select("SELECT COUNT(*) FROM seat")
    int getSeatCount();

    @Select("SELECT * FROM seat WHERE seat_id = #{seatId}")
    Seat getSeatById(String seatId);

    @Select("SELECT * FROM seat WHERE hall_id = #{hallId} ORDER BY s_row, s_column")
    List<Seat> getAllSeats(String hallId);
}
