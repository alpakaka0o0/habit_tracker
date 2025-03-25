package com.example.backend.controller;

import com.example.backend.entity.Habit;
import com.example.backend.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/habits")
@CrossOrigin(origins = "http://localhost:3000")
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public Flux<Habit> getAllHabits() {
        return habitService.getAllHabits();
    }

    @GetMapping("/active")
    public Flux<Habit> getActiveHabits() {
        return habitService.getActiveHabits();
    }

    @PostMapping
    public Mono<Habit> createHabit(@RequestBody Habit habit) {
        return habitService.createHabit(habit);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Habit>> updateHabit(@PathVariable Long id, @RequestBody Habit habitDetails) {
        return habitService.updateHabit(id, habitDetails)
                .map(ResponseEntity::ok)
                .onErrorResume(e->Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> softDeleteHabit(@PathVariable Long id) {
        return habitService.softDeleteHabit(id)
                .map(deleted -> ResponseEntity.noContent().<Void>build())
                .onErrorResume(e-> Mono.just(ResponseEntity.notFound().build()));
    }
}
