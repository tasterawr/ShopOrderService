package org.loktevik.netcracker.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Status;
import org.loktevik.netcracker.repository.StatusRepository;
import org.loktevik.netcracker.service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("statuses")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @GetMapping("{id}")
    public ResponseEntity<Status> getStatus(@PathVariable Long id) {
        Status status = statusService.getById(id);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuss = statusService.getAll();

        return new ResponseEntity<>(statuss, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Status> saveStatus(@RequestBody Status status){
        statusService.saveStatus(status);

        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long id){
        statusService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Status> updateStatus(@RequestBody Status status){
        statusService.updateStatus(status);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("data")
    public ResponseEntity<Status> sendStatus(){
        Status status = new Status();
        status.setName("Доставка");
        statusService.saveStatus(status);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
