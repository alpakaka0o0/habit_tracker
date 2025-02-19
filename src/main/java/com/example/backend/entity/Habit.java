package com.example.backend.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
=======

>>>>>>> 40652a7039b5747e1899bb65157e6a8b4a9439d1
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "habit")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;
<<<<<<< HEAD
    @Column(nullable = false)
    private LocalDate endDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE; // 습관 상태 (기본값: ACTIVE)
=======

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();;
    private LocalDateTime deletedAt;

    public void setUpdatedAt(LocalDateTime now) {
        this.updatedAt = now;
    }

    public void setDeletedAt(LocalDateTime now) {
        this.deletedAt = now;
    }
>>>>>>> 40652a7039b5747e1899bb65157e6a8b4a9439d1

    public enum Status {
        ACTIVE, PAUSED, DELETED
    }

    public Habit() {}

<<<<<<< HEAD
    public Habit(String name, LocalDate startDate, LocalDate endDate, Status status) {
=======
    public Habit( String name, LocalDate startDate, LocalDate endDate, Status status) {
>>>>>>> 40652a7039b5747e1899bb65157e6a8b4a9439d1
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

<<<<<<< HEAD
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
=======
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

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
>>>>>>> 40652a7039b5747e1899bb65157e6a8b4a9439d1
