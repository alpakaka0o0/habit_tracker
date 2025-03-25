package com.example.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Table("habit_log")
public class HabitLog {

    @Id
    private Long id;

    @Column("habit_id")
    private Long habitId;

    @Column("completed_date")
    private LocalDateTime completedDate;

    public HabitLog() {}

    public HabitLog(Long habitId, LocalDateTime completedDate) {
        this.habitId = habitId;
        this.completedDate = completedDate;
    }

    public Long getId() {
        return id;
    }

    public Long getHabitId() {
        return habitId;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }
}