package com.example.backend.HabitLog;

import com.example.backend.entity.Habit;
import com.example.backend.entity.HabitLog;
import com.example.backend.repository.HabitLogRepository;
import com.example.backend.service.HabitLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitLogServiceTest {

    @Mock
    private HabitLogRepository habitLogRepository;

    @InjectMocks
    private HabitLogService habitLogService;

    private Habit habit;
    private HabitLog habitLog1;
    private HabitLog habitLog2;

    @BeforeEach
    void setUp() {
        habit = new Habit("Morning Run", LocalDate.now(), LocalDate.now().plusDays(30), Habit.Status.ACTIVE);
        habit.setId(1L);

        habitLog1 = new HabitLog(habit.getId(), LocalDateTime.now().minusDays(1));
        habitLog1.setId(1L);

        habitLog2 = new HabitLog(habit.getId(), LocalDateTime.now());
        habitLog2.setId(2L);
    }

    @Test
    @DisplayName("✅ 전체 HabitLog 조회")
    void testGetAllHabitLogs() {
        when(habitLogRepository.findAll()).thenReturn(Flux.just(habitLog1, habitLog2));

        List<HabitLog> logs = habitLogService.getAllHabitLogs().collectList().block();
        assertEquals(2, logs.size());
    }

    @Test
    @DisplayName("✅ 특정 ID로 HabitLog 조회")
    void testGetHabitLogById() {
        when(habitLogRepository.findById(1L)).thenReturn(Mono.just(habitLog1));

        HabitLog result = habitLogService.getHabitLogById(1L).block();
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("✅ HabitLog 저장")
    void testSaveHabitLog() {
        when(habitLogRepository.save(any(HabitLog.class))).thenReturn(Mono.just(habitLog1));

        HabitLog saved = habitLogService.saveHabitLog(habitLog1).block();
        assertNotNull(saved);
        assertEquals(habit.getId(), saved.getHabitId());
    }

    @Test
    @DisplayName("✅ HabitLog 삭제")
    void testDeleteHabitLog() {
        when(habitLogRepository.deleteById(1L)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> {
            habitLogService.deleteHabitLog(1L).block();
        });
        verify(habitLogRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("✅ 존재하지 않는 HabitLog 조회 시 Mono.empty()")
    void testGetHabitLog_NotFound() {
        when(habitLogRepository.findById(999L)).thenReturn(Mono.empty());

        HabitLog result = habitLogService.getHabitLogById(999L).block();
        assertNull(result);
    }
}