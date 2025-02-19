package com.example.backend.repository;

import com.example.backend.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByStatus(Habit.Status status);
}
