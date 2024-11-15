package com.example.sessions.controller;

import com.example.api.client.FilmClient;
import com.example.api.po.Film;
import com.example.sessions.domain.po.Hall;
import com.example.sessions.domain.po.Session;
import com.example.sessions.domain.vo.Result;
import com.example.sessions.domain.vo.SeatJsonDetail;
import com.example.sessions.domain.vo.SessionDetail;
import com.example.sessions.domain.vo.SessionsList;
import com.example.sessions.service.HallService;
import com.example.sessions.service.SeatService;
import com.example.sessions.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Tag(name = "场次相关接口")
@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @Autowired
    private FilmClient filmClient;

    private final HallService hallService;

    private final SeatService seatService;

    @Operation(summary = "获取所有场次")
    @GetMapping("/list")
    public Result<SessionsList> list(
            @RequestParam(required = false) String filmName,
            @RequestParam(required = false) String time,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        SessionsList sessionsList = new SessionsList();
        List<SessionDetail> sessions = new ArrayList<>();

        if (filmName != null && !filmName.isEmpty()) {
            List<Film> films = filmClient.getFilmByName(filmName);

            if (films.isEmpty()) {
                return Result.error("影片不存在");
            }

            if (films.size() == 1) {
                System.out.println(films.get(0).getFilmId());
                sessions.addAll(sessionService.getSessions(films.get(0).getFilmId(), time, current, pageSize));
                sessionsList.setCount(sessions.size());
            } else {
                for (Film film : films) {
                    System.out.println(film.getFilmId());
                    sessions.addAll(sessionService.getSessions(film.getFilmId(), time, current, pageSize));
                }
                sessionsList.setCount(sessions.size());
            }

        }
        if (sessions.isEmpty()) {
            sessions.addAll(sessionService.getSessions(null, time, current, pageSize));
            sessionsList.setCount(sessionService.Count());
        }
        sessionsList.setPage(current);
        sessionsList.setSize(pageSize);
        sessionsList.setList(sessions);
        return Result.success(sessionsList);
    }

    @Operation(summary = "添加场次")
    @PostMapping("/add")
    public Result<String> add(@RequestBody SessionDetail session) {

        session.setSessionId(UUID.randomUUID().toString());
        session.setFilmId(filmClient.getFilmByName(session.getFilmName()).get(0).getFilmId());
        Hall hall = hallService.queryHalls(session.getHallName(), null, 1, 10).get(0);
        session.setHallId(hall.getHallId());

        if (hall.getSeatJsonId() == null) {
            return Result.error("影厅不存在");
        }
        session.setSeatJson(seatService.getSeatJsonById(hall.getSeatJsonId()));

        if (sessionService.getHallByTime(session.getTime()).equals(session.getHallName())) {
            return Result.error("时间冲突");
        }

        if (sessionService.addSession(session) == 1) {
            return Result.success("创建成功");
        }

        return Result.error("创建失败");
    }

    @Operation(summary = "删除场次")
    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam String id) {
        if (sessionService.deleteSession(id) == 1) {
            return Result.success("删除成功");
        }

        return Result.error("删除失败");
    }

    @Operation(summary = "修改场次")
    @PutMapping("/update")
    public Result<String> update(@RequestBody SessionDetail session) {

        List<String> sessionList = sessionService.getHallByTime(session.getTime());
        session.setFilmId(filmClient.getFilmByName(session.getFilmName()).get(0).getFilmId());

        Hall hall = hallService.queryHalls(session.getHallName(), null, 1, 10).get(0);
        session.setHallId(hall.getHallId());

        if (hall.getSeatJsonId() == null) {
            return Result.error("影厅不存在");
        }
        session.setSeatJson(seatService.getSeatJsonById(hall.getSeatJsonId()));

        if (!session.getHallName().equals(hallService.getHallById(sessionService.getHallById(session.getSessionId())).getHallName())) {
            System.out.println(1);
            for (String hallId : sessionList) {
                if (hallId.equals(session.getHallId())) {
                    return Result.error("时间冲突");
                }
            }
        }

        if (sessionService.updateSession(session) == 1) {
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @Operation(summary = "通过电影Id获取场次")
    @GetMapping("/getFilmSessions")
    public Result<List<SessionDetail>> getFilmSessions(
            @RequestParam(required = false) String filmId,
            @RequestParam(required = false) String time
    ) {
        List<SessionDetail> sessions = new ArrayList<>();

        sessions.addAll(sessionService.getSessionsByFilmId(filmId, time));


        return Result.success(sessions);
    }

    @Operation(summary = "通过场次Id获取座位图")
    @GetMapping("/getSeatJson")
    public Result<String> getSessionDetail(@RequestParam String sessionId) {
        Session sessionDetail = sessionService.getSeatMap(sessionId);

        if (sessionDetail.getSeatJson() == null) {
            return Result.error("获取失败");
        } else {
            return Result.success(sessionDetail.getSeatJson());
        }
    }

    @Operation(summary = "修改座位图")
    @PutMapping("/updateSeatJson")
    @GlobalTransactional(rollbackFor = Exception.class)
    public Result<String> updateSeatJson(
            @RequestParam String sessionId,
            @RequestBody List<String> seatIds) throws JsonProcessingException {
        int num = 0;
        String seatJson = sessionService.getSeatMap(sessionId).getSeatJson();
        ObjectMapper objectMapper = new ObjectMapper();
        SeatJsonDetail[] seatJsonDetails = objectMapper.readValue(seatJson, SeatJsonDetail[].class);
        for (SeatJsonDetail seatJsonDetail : seatJsonDetails) {
            for (int i = 1; i <= seatJsonDetail.getSeats().size(); i++) {
                if (seatIds.contains(seatJsonDetail.getSeats().get(i - 1).getId())) {
                    System.out.println("seatId:" + seatJsonDetail.getSeats().get(i - 1).getId());
                    seatJsonDetail.getSeats().get(i - 1).setStatus("a");
                    num++;
                }
            }
        }

        seatJson = objectMapper.writeValueAsString(seatJsonDetails);

        if (num != seatIds.size()) {
            return Result.error("修改失败"+num+" "+seatIds.size());
        }
        if (sessionService.updateSeatJson(sessionId, seatJson) != 1) {
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }
}
