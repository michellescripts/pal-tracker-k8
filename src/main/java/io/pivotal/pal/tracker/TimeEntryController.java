package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/time-entries")
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TimeEntry> create(
            @RequestBody TimeEntry timeEntryToCreate
    ) {
        var created = timeEntryRepository.create(timeEntryToCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping(path = "/{timeEntryId}")
    @ResponseBody
    public ResponseEntity<TimeEntry> read(
            @PathVariable long timeEntryId
    ) {
        TimeEntry ret = timeEntryRepository.find(timeEntryId);
        return ret == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(timeEntryRepository.list());
    }

    @PutMapping(path = "/{timeEntryId}")
    @ResponseBody
    public ResponseEntity update(
            @PathVariable long timeEntryId,
            @RequestBody TimeEntry timeEntry
    ) {
        TimeEntry ret = timeEntryRepository.update(timeEntryId, timeEntry);
        return ret == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @DeleteMapping(path = "/{timeEntryId}")
    @ResponseBody
    public ResponseEntity delete(
            @PathVariable long timeEntryId
    ) {
        timeEntryRepository.delete(timeEntryId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

}
