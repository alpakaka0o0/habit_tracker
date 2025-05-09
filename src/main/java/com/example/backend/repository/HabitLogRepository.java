package com.example.backend.repository;

import com.example.backend.entity.HabitLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitLogRepository extends ReactiveCrudRepository<HabitLog, Long> {

}
