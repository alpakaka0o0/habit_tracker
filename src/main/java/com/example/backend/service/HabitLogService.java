package com.example.backend.service;

import com.example.backend.entity.HabitLog;
import com.example.backend.repository.HabitLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitLogService {
    private final HabitLogRepository habitLogRepository;

    public HabitLogService(HabitLogRepository habitLogRepository) {
        this.habitLogRepository = habitLogRepository;
    }

    public List<HabitLog> getAllHabitLogs() {
        return habitLogRepository.findAll();
    }

    public Optional<HabitLog> getHabitLogById(Long id) {
        return getHabitLogById(id);
    }

    public HabitLog saveHabitLog(HabitLog habitLog) {
        return habitLogRepository.save(habitLog);
    }

    public void deleteHabitLog(Long id) {
        habitLogRepository.deleteById(id);
    }
}
