package com.example.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "habit")
public class Habit {
    @Id
    private Long id;


    private String name;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    private String status = Status.ACTIVE.name();

    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column("updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    public void setUpdatedAt(LocalDateTime now) {
        this.updatedAt = now;
    }

    public void setDeletedAt(LocalDateTime now) {
        this.deletedAt = now;
    }

    public enum Status {
        ACTIVE, PAUSED, DELETED
    }

    public Habit() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = Status.ACTIVE.name();
    }

    public Habit(String name, LocalDate startDate, LocalDate endDate, Status status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status.name();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return Status.valueOf(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.name();
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
