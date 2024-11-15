package com.example.sessions.mapper;

import com.example.sessions.domain.po.Seat;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeatMapper {

    @Select("SELECT * FROM seat WHERE seat_json_id = #{seatJsonId} ORDER BY s_row, s_column LIMIT #{current}, #{pageSize}")
    List<Seat> getSeats(@Param("hallId") String hallId, @Param("current") int current, @Param("pageSize") int pageSize);

    @Update("UPDATE seat SET seat_type = #{seatType}, status = #{status} WHERE seat_id = #{seatId}")
    int updateSeat(Seat seat);

    @Insert("INSERT INTO seat (seat_id, seat_json_id, s_row, s_column, seat_type, status)" +
            " VALUES (#{seatId}, #{seatJsonId}, #{sRow}, #{sColumn}, #{seatType}, #{status})")
    int addSeat(Seat seat);

    @Select("SELECT COUNT(*) FROM seat")
    int getSeatCount();

    @Select("SELECT * FROM seat WHERE seat_id = #{seatId}")
    Seat getSeatById(String seatId);

    @Select("SELECT * FROM seat WHERE seat_json_id = #{seatJsonId} ORDER BY s_row, s_column")
    List<Seat> getAllSeats(String hallId);

    @Select("SELECT seat_json.seat_json FROM seat_json where seat_json.seat_json_id = #{seatJsonId}")
    String getSeatJsonById(String seatJsonId);

    @Insert("INSERT INTO seat_json (seat_json_id, seat_json) VALUES (#{seatJsonId}, #{seatJson})")
    int addSeatJson(String seatJsonId, String seatJson);
}
