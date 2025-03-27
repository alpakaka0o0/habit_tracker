package com.example.backend.Habit;

import com.example.backend.entity.Habit;
import com.example.backend.repository.HabitRepository;
import com.example.backend.service.HabitService;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito 사용
class HabitServiceTest {

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitService habitService;

    private Habit testHabit1;
    private Habit testHabit2;

    @BeforeEach
    void setUp() {
        testHabit1 = new Habit("Morning Run", LocalDate.now(), null, Habit.Status.ACTIVE);
        testHabit1.setId(1L);

        testHabit2 = new Habit("Reading", LocalDate.now(), null, Habit.Status.PAUSED);
        testHabit2.setId(2L);
    }


    @Test
    @DisplayName("정상적인 습관 생성")
    void testCreateHabit() {
        when(habitRepository.save(any(Habit.class))).thenReturn(Mono.just(testHabit1));

        Habit savedHabit = habitService.createHabit(testHabit1).block();
        assertNotNull(savedHabit);
        assertEquals("Morning Run", savedHabit.getName());
        verify(habitRepository, times(1)).save(any(Habit.class));
    }

    @Test
    @DisplayName("✅ endDate가 null일 때 기본값 (3개월 후) 설정 확인")
    void testCreateHabit_EndDateIsNull() {
        Habit habit = new Habit("New Habit", LocalDate.now(), null, Habit.Status.ACTIVE);

        when(habitRepository.save(any(Habit.class))).thenReturn(habitService.createHabit(habit));

        Habit savedHabit = habitService.createHabit(habit).block();
        assertEquals(LocalDate.now().plusMonths(3), savedHabit.getEndDate());
    }

    // ✅ all_ok → 전체 목록 조회 정상 작동 확인
    @Test
    @DisplayName("✅ 전체 습관 목록 조회 정상 작동 확인")
    void testGetAllHabits() {
        when(habitRepository.findAll()).thenReturn(Flux.just(testHabit1, testHabit2));

        List<Habit> habits = habitService.getAllHabits().collectList().block();
        assertEquals(2, habits.size());
    }

    // ✅ active_ok → 활성화된 습관만 조회 정상 작동 확인
    @Test
    @DisplayName("✅ 활성화된 습관만 조회 정상 작동 확인")
    void testGetActiveHabits() {
        when(habitRepository.findByStatus(Habit.Status.ACTIVE.name())).thenReturn(Flux.just(testHabit1));

        List<Habit> activeHabits = habitService.getActiveHabits().collectList().block();
        assertEquals(1, activeHabits.size());
        assertEquals(Habit.Status.ACTIVE, activeHabits.get(0).getStatus());
    }

    // ➕ empty_list → DB에 저장된 습관이 없을 때 빈 리스트 반환 확인
    @Test
    @DisplayName("➕ 습관 목록이 없을 때 빈 리스트 반환 확인")
    void testGetHabits_EmptyList() {
        when(habitRepository.findAll()).thenReturn(Flux.empty());

        List<Habit> habits = habitService.getAllHabits().collectList().block();
        assertTrue(habits.isEmpty());
    }

    // ✅ ok → 정상적인 수정
    @Test
    @DisplayName("✅ 정상적인 습관 수정")
    void testUpdateHabit() {
        when(habitRepository.findById(1L)).thenReturn(Mono.just(testHabit1));
        when(habitRepository.save(any(Habit.class))).thenReturn(Mono.just(testHabit1));

        testHabit1.setName("Evening Walk");
        Habit updatedHabit = habitService.updateHabit(1L, testHabit1).block();

        assertEquals("Evening Walk", updatedHabit.getName());
    }

    // ✅ invalid → 존재하지 않는 id 수정 요청 시 예외 발생 확인
    @Test
    @DisplayName("✅ 존재하지 않는 id 수정 요청 시 예외 발생 확인")
    void testUpdateHabit_InvalidId() {
        when(habitRepository.findById(999L)).thenReturn(Mono.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            habitService.updateHabit(999L, testHabit1).block();
        });
    }

    // ➕ status_update_only → 상태(status) 필드만 변경
    @Test
    @DisplayName("➕ 상태(status) 필드만 변경하는 경우 정상 동작 확인")
    void testUpdateHabit_StatusOnly() {
        when(habitRepository.findById(1L)).thenReturn(Mono.just(testHabit1));
        when(habitRepository.save(any(Habit.class))).thenReturn(Mono.just(testHabit1));

        testHabit1.setStatus(Habit.Status.PAUSED);
        Habit updatedHabit = habitService.updateHabit(1L, testHabit1).block();

        assertEquals(Habit.Status.PAUSED, updatedHabit.getStatus());
    }

    // ✅ ok → 정상적인 삭제 요청
    @Test
    @DisplayName("✅ 정상적인 습관 삭제 요청")
    void testSoftDeleteHabit() {
        when(habitRepository.findById(1L)).thenReturn(Mono.just(testHabit1));
        when(habitRepository.save(any(Habit.class))).thenReturn(Mono.just(testHabit1));

        Habit deleted = habitService.softDeleteHabit(1L).block();
        assertEquals(Habit.Status.DELETED, deleted.getStatus());
        assertNotNull(deleted.getDeletedAt());
    }

    // ✅ invalid_id → 존재하지 않는 id 삭제 요청 시 예외 발생 확인
    @Test
    @DisplayName("✅ 존재하지 않는 id 삭제 요청 시 예외 발생 확인")
    void testSoftDeleteHabit_InvalidId() {
        when(habitRepository.findById(999L)).thenReturn(Mono.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            habitService.softDeleteHabit(999L).block();
        });
    }

    // ➕ already_deleted → 이미 삭제된 습관을 다시 삭제할 때 예외 발생
    @Test
    @DisplayName("➕ 이미 삭제된 습관을 다시 삭제할 때 예외 발생 확인")
    void testSoftDeleteHabit_AlreadyDeleted() {
        testHabit1.setStatus(Habit.Status.DELETED);
        when(habitRepository.findById(1L)).thenReturn(Mono.just(testHabit1));

        assertThrows(IllegalArgumentException.class, () -> {
            habitService.softDeleteHabit(1L).block();
        });
    }
}