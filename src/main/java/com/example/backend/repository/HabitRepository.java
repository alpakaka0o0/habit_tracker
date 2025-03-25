package com.example.backend.repository;

import com.example.backend.entity.Habit;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;


@Repository
public interface HabitRepository extends ReactiveCrudRepository<Habit, Long> {
    Flux<Habit> findByStatus(String status);

}
