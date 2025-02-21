package com.example.backend.controller;

import com.example.backend.entity.HabitLog;
import com.example.backend.service.HabitLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitLogs")
@CrossOrigin(origins = "http://localhost:3000")
public class HabitLogController {
    private final HabitLogService habitLogService;

    public HabitLogController(HabitLogService habitLogService) {
        this.habitLogService = habitLogService;
    }

    @GetMapping
    public List<HabitLog> getAllHabitLog() {
        return habitLogService.getAllHabitLogs();
    }
    @GetMapping("/{id}")
    public Optional<HabitLog> getHabitLogById(@PathVariable Long id) {
        return habitLogService.getHabitLogById(id);
    }

    @PostMapping
    public HabitLog createHabitLog(@RequestBody HabitLog habitLog) {
        return habitLogService.saveHabitLog(habitLog);
    }

    @DeleteMapping("/{id}")
    public void deleteHabitLogById(@PathVariable Long id) {
        habitLogService.deleteHabitLog(id);
    }
}
