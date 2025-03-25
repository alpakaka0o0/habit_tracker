package com.example.backend.controller;

import com.example.backend.entity.HabitLog;
import com.example.backend.service.HabitLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<HabitLog> getAllHabitLog() {
        return habitLogService.getAllHabitLogs();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<HabitLog>> getHabitLogById(@PathVariable Long id) {
        return habitLogService.getHabitLogById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<HabitLog> createHabitLog(@RequestBody HabitLog habitLog) {
        return habitLogService.saveHabitLog(habitLog);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteHabitLogById(@PathVariable Long id) {
        return habitLogService.deleteHabitLog(id)
                .thenReturn(ResponseEntity.noContent().<Void>build())
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
