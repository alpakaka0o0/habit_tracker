package com.example.backend.service;

import com.example.backend.entity.HabitLog;
import com.example.backend.repository.HabitLogRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class HabitLogService {
    private final HabitLogRepository habitLogRepository;

    public HabitLogService(HabitLogRepository habitLogRepository) {
        this.habitLogRepository = habitLogRepository;
    }

    public Flux<HabitLog> getAllHabitLogs() {
        return habitLogRepository.findAll();
    }

    public Mono<HabitLog> getHabitLogById(Long id) {
        return getHabitLogById(id);
    }

    public Mono<HabitLog> saveHabitLog(HabitLog habitLog) {
        return habitLogRepository.save(habitLog);
    }

    public Mono<Void> deleteHabitLog(Long id) {
        return habitLogRepository.deleteById(id);
    }
}
