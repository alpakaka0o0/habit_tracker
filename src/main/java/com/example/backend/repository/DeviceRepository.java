package com.example.backend.repository;

import com.example.backend.entity.Device;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends ReactiveCrudRepository<Device, String> {
}