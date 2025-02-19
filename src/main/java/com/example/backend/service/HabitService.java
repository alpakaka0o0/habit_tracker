package com.example.backend.service;

import com.example.backend.entity.Habit;
import com.example.backend.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public List<Habit> getActiveHabits() {
        return habitRepository.findByStatus(Habit.Status.ACTIVE);
    }

    public Habit createHabit(Habit habit) {
        if (habit.getEndDate() == null) {
            habit.setEndDate(habit.getStartDate().plusMonths(3)); // 기본적으로 3개월 뒤 설정
        }
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long id, Habit habitDetails) {
        Habit habit = habitRepository.findById(id).orElseThrow();
        habit.setName(habitDetails.getName());
        habit.setStartDate(habitDetails.getStartDate());
        habit.setEndDate(habitDetails.getEndDate());
        habit.setUpdatedAt(LocalDateTime.now());
        habit.setStatus(habitDetails.getStatus());
        return habitRepository.save(habit);
    }

    public void softDeleteHabit(Long id) {
        Habit habit = habitRepository.findById(id).orElseThrow();
        habit.setDeletedAt(LocalDateTime.now());
        habit.setStatus(Habit.Status.DELETED);
        habitRepository.save(habit);
    }
}
