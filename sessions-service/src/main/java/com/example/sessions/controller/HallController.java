package com.example.sessions.controller;

import com.example.sessions.domain.po.Hall;
import com.example.sessions.domain.vo.HallList;
import com.example.sessions.domain.vo.Result;
import com.example.sessions.service.HallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "影厅相关接口")
@RestController
@RequestMapping("/api/hall")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;

    @Operation(summary = "创建影厅")
    @PostMapping("/createHall")
    public Result<String> createHall(@RequestBody Hall hall) {
        hall.setHallId(UUID.randomUUID().toString());

        if (hallService.addHall(hall) == 1) {
            return Result.success("创建成功");
        }

        return Result.error("创建失败");
    }

    @Operation(summary = "删除影厅")
    @DeleteMapping("/delete")
    public Result<String> deleteHall(@RequestParam String id) {
        hallService.deleteHall(id);
        return Result.success("删除成功");
    }

    @Operation(summary = "修改影厅信息")
    @PutMapping("/modifyHall")
    public Result<String> modifyHall(@RequestBody Hall hall) {
        if (hallService.updateHall(hall) == 1) {
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @Operation(summary = "查询影厅")
    @GetMapping("/list")
    public Result<HallList> queryAllHalls(
            @RequestParam(required = false) String hallName,
            @RequestParam(required = false) String hallDesc,
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        HallList hallList = new HallList();
        List<Hall> halls = hallService.queryHalls(hallName, hallDesc, current, pageSize);
        hallList.setPage(current);
        hallList.setSize(pageSize);
        hallList.setCount(hallService.getHallsCount());
        hallList.setList(halls);
        return Result.success(hallList);
    }

    @Operation(summary = "添加座位图")
    @PostMapping("/addSeatMap")
    public Result<String> addSeatMap(@RequestParam String hallId, @RequestParam String seatJson) {
        if (hallService.addSeatMap(hallId, seatJson) == 1) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @GetMapping("/getHallForName")
    public List<String>  getHallForName() {
        return hallService.getHallForName();
    }

    @GetMapping("/getHallById")
    public Hall getHallById(@RequestParam String hallId) {
        return hallService.getHallById(hallId);
    }
}
