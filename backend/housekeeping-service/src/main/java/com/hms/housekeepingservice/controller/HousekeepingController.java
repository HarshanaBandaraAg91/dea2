package com.hms.housekeepingservice.controller;

import com.hms.housekeepingservice.entity.HousekeepingTask;
import com.hms.housekeepingservice.service.HousekeepingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tasks")
public class HousekeepingController {

    private final HousekeepingService service;

    public HousekeepingController(HousekeepingService service) {
        this.service = service;
    }

    /** Health check */
    @GetMapping("/test")
    public String test() {
        return "API OK";
    }

    /** Create a new housekeeping task */
    @PostMapping
    public HousekeepingTask create(@RequestBody HousekeepingTask task) {
        return service.create(task);
    }

    /** Get all tasks */
    @GetMapping
    public List<HousekeepingTask> all() {
        return service.all();
    }

    /** Get task by ID */
    @GetMapping("/{id}")
    public HousekeepingTask getById(@PathVariable Long id) {
        return service.findById(id);
    }

    /** Update an existing task */
    @PutMapping("/{id}")
    public HousekeepingTask update(@PathVariable Long id,
                                   @RequestBody HousekeepingTask task) {
        return service.update(id, task);
    }

    /** Delete a task */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Get tasks by room number */
    @GetMapping("/room/{roomNumber}")
    public List<HousekeepingTask> byRoom(@PathVariable String roomNumber) {
        return service.findByRoomNumber(roomNumber);
    }

    /** Get tasks by status */
    @GetMapping("/status/{status}")
    public List<HousekeepingTask> byStatus(@PathVariable String status) {
        return service.findByStatus(status);
    }
}
