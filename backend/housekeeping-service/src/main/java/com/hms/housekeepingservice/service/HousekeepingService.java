package com.hms.housekeepingservice.service;

import com.hms.housekeepingservice.entity.HousekeepingTask;
import com.hms.housekeepingservice.repository.HousekeepingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HousekeepingService {

    private final HousekeepingRepository repo;

    public HousekeepingService(HousekeepingRepository repo) {
        this.repo = repo;
    }

    public HousekeepingTask create(HousekeepingTask task) {
        return repo.save(task);
    }

    public List<HousekeepingTask> all() {
        return repo.findAll();
    }

    public HousekeepingTask findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Task not found with id: " + id));
    }

    public HousekeepingTask update(Long id, HousekeepingTask updated) {
        HousekeepingTask existing = findById(id);
        existing.setRoomNumber(updated.getRoomNumber());
        existing.setTaskType(updated.getTaskType());
        existing.setStatus(updated.getStatus());
        existing.setAssignedStaff(updated.getAssignedStaff());
        existing.setTaskDate(updated.getTaskDate());
        existing.setNotes(updated.getNotes());
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Task not found with id: " + id);
        }
        repo.deleteById(id);
    }

    public List<HousekeepingTask> findByRoomNumber(String roomNumber) {
        return repo.findByRoomNumber(roomNumber);
    }

    public List<HousekeepingTask> findByStatus(String status) {
        return repo.findByStatus(status);
    }
}
