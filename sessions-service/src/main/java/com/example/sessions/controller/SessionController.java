package com.example.sessions.controller;

import com.example.api.client.FilmClient;
import com.example.api.po.Film;
import com.example.sessions.domain.po.Session;
import com.example.sessions.domain.vo.Result;
import com.example.sessions.domain.vo.SessionDetail;
import com.example.sessions.domain.vo.SessionsList;
import com.example.sessions.service.HallService;
import com.example.sessions.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Tag(name = "场次相关接口")
@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    private FilmClient filmClient;

    private final HallService hallService;

    @Operation(summary = "获取所有场次")
    @GetMapping("/list")
    public Result<SessionsList> list(
            @RequestParam(required = false) String filmName,
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        SessionsList sessionsList = new SessionsList();
        List<SessionDetail> sessions = new ArrayList<>();

        if (filmName != null && !filmName.isEmpty()){
            List<Film> films = filmClient.getFilmByName(filmName);
            if (films.isEmpty()){
                return Result.error("影片不存在");
            }
            for (Film film : films){
                sessions.addAll(sessionService.getSessionsByFilmId(film.getFilmId(), current, pageSize));
            }
        }
        sessions.addAll(sessionService.getSessionsByFilmId(null, current, pageSize));
        sessionsList.setCount(sessionService.Count());
        sessionsList.setList(sessions);
        sessionsList.setSize(pageSize);
        sessionsList.setPage(current);
        return Result.success(sessionsList);
    }

    @Operation(summary =  "添加场次")
    @PostMapping("/add")
    public Result<String> add(@RequestBody SessionDetail session) {

        session.setSessionId(UUID.randomUUID().toString());
        session.setFilmId(filmClient.getFilmByName(session.getFilmName()).get(0).getFilmId());
        session.setHallId(hallService.queryHalls(session.getHallName(),null, 1, 10).get(0).getHallId());
        System.out.println(session);

        if (sessionService.getHallByTime(session.getTime()).equals(session.getHallName())){
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
        session.setHallId(hallService.queryHalls(session.getHallName(),null, 1, 10).get(0).getHallId());

        System.out.println(hallService.getHallById(sessionService.getHallById(session.getSessionId())));

        if(!session.getHallName().equals(hallService.getHallById(sessionService.getHallById(session.getSessionId())).getHallName())){
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
    public Result<SessionsList> getFilmSessions(
            @RequestParam(required = false) String filmId,
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {

        SessionsList sessionsList = new SessionsList();
        List<SessionDetail> sessions = new ArrayList<>();

        sessions.addAll(sessionService.getSessionsByFilmId(filmId, current, pageSize));

        sessionsList.setCount(sessionService.Count());
        sessionsList.setList(sessions);
        sessionsList.setSize(pageSize);
        sessionsList.setPage(current);

        return Result.success(sessionsList);
    }
}
