package com.example.backend.service;

import com.example.backend.entity.Device;
import com.example.backend.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Flux<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Mono<Device> getDeviceById(String id) {
        return deviceRepository.findById(id);
    }

    public Mono<Device> createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Mono<Device> updateDevice(String id, Device device) {
        return deviceRepository.findById(id)
                .flatMap(existingDevice -> {
                    existingDevice.setName(device.getName());
                    existingDevice.setType(device.getType());
                    return deviceRepository.save(existingDevice);
                });
    }

    public Mono<Void> deleteDevice(String id) {
        return deviceRepository.deleteById(id);
    }
}
