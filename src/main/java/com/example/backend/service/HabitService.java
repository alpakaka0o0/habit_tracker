package com.example.backend.service;

import com.example.backend.entity.Habit;
import com.example.backend.repository.HabitRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public Flux<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Flux<Habit> getActiveHabits() {
        return habitRepository.findByStatus(Habit.Status.ACTIVE.name());
    }

    public Mono<Habit> createHabit(Habit habit) {
        if (habit.getEndDate() == null) {
            habit.setEndDate(habit.getStartDate().plusMonths(3)); // 기본적으로 3개월 뒤 설정
        }
        return habitRepository.save(habit);
    }

    public Mono<Habit> updateHabit(Long id, Habit habitDetails) {
        return habitRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Habit not found")))
                .flatMap(habit -> {
                    habit.setName(habitDetails.getName());
                    habit.setEndDate(habitDetails.getEndDate());
                    habit.setUpdatedAt(LocalDateTime.now());
                    habit.setStatus(habitDetails.getStatus());
                    return habitRepository.save(habit);
                });
    }

    public Mono<Habit> softDeleteHabit(Long id) {
        return habitRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Habit not found")))
                .flatMap(habit -> {
                    if (Habit.Status.DELETED.equals(habit.getStatus())) {
                        return Mono.error(new IllegalArgumentException("Already deleted"));
                    }
                    habit.setDeletedAt(LocalDateTime.now());
                    habit.setStatus(Habit.Status.DELETED);
                    return habitRepository.save(habit);
                });
    }
}
