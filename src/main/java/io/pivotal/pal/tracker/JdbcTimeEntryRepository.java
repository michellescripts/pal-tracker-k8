package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long id = jdbcTemplate.queryForObject(
            "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?) RETURNING id",
            new Object[]{
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours()
            },
            Integer.class
        );

        return find(id);
    }

    @Override
    public TimeEntry find(Long id) {
        return jdbcTemplate.query(
            "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
            new Object[]{id},
            extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("SELECT id, project_id, user_id, date, hours FROM time_entries ORDER BY id", mapper);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        jdbcTemplate.update("UPDATE time_entries " +
                "SET project_id = ?, user_id = ?, date = ?,  hours = ? " +
                "WHERE id = ?",
            timeEntry.getProjectId(),
            timeEntry.getUserId(),
            Date.valueOf(timeEntry.getDate()),
            timeEntry.getHours(),
            id);

        return find(id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?", id);
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
        rs.getLong("id"),
        rs.getLong("project_id"),
        rs.getLong("user_id"),
        rs.getDate("date").toLocalDate(),
        rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
        (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
