package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Map<Long, TimeEntry> data = new HashMap<>();
    private long currentId =1L;

    public TimeEntry create(TimeEntry timeEntry) {
        long newId = this.currentId++;
        TimeEntry t = new TimeEntry(newId, timeEntry);
        data.put(newId, t);
        return t;
    }

    public TimeEntry find(long id) {
        return data.get(id);
    }

    public List<TimeEntry> list() {
        return List.copyOf(data.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        data.replace(id, timeEntry);
        return data.get(id);
    }

    public void delete(long id) {
        data.remove(id);
    }
}
