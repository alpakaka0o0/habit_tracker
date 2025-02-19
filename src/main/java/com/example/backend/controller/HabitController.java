package com.example.backend.controller;

import com.example.backend.entity.Habit;
import com.example.backend.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public List<Habit> getAllHabits() {
        return habitService.getAllHabits();
    }

    @GetMapping("/active")
    public List<Habit> getActiveHabits() {
        return habitService.getActiveHabits();
    }

    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        return habitService.createHabit(habit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long id, @RequestBody Habit habitDetails) {
        return ResponseEntity.ok(habitService.updateHabit(id, habitDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteHabit(@PathVariable Long id) {
        habitService.softDeleteHabit(id);
        return ResponseEntity.noContent().build();
    }
}
