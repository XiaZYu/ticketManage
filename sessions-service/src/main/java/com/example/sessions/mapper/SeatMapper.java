package com.example.sessions.mapper;

import com.example.sessions.domain.po.Seat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SeatMapper {

    @Select("SELECT * FROM seat WHERE hall_id = #{hallId} ORDER BY s_row, s_column LIMIT #{size}, #{pageSize}")
    List<Seat> getSeats(String hallId, int size, int pageSize);

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
