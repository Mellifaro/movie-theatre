package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcEventDaoImpl implements EventDAO {

    private static final BeanPropertyRowMapper<Event> EVENT_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Event.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuditoriumDAO auditoriumDAO;

    private SimpleJdbcInsert insertEvent;

    @Autowired
    public JdbcEventDaoImpl(DataSource dataSource){
        insertEvent = new SimpleJdbcInsert(dataSource)
                .withTableName("events")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    //todo split method
    public Optional<Event> getByName(String name) {
        List<Event> events = jdbcTemplate.query("SELECT * FROM events WHERE events.name=?", EVENT_ROW_MAPPER, name);
        events.forEach(event -> {
            Map<LocalDateTime, String> resSet = jdbcTemplate.query("SELECT event_date, auditorium_name FROM dates WHERE dates.event_id=?", new ResultSetExtractor<Map<LocalDateTime, String>>() {
                @Override
                public Map<LocalDateTime, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    Map<LocalDateTime, String> mapRet = new HashMap<>();
                    while (rs.next()) {
                        mapRet.put(rs.getTimestamp("event_date").toLocalDateTime(), rs.getString("auditorium_name"));
                    }
                    return mapRet;
                }
            }, event.getId());
            event.setAirDates(new TreeSet<>(resSet.keySet()));

            NavigableMap<LocalDateTime, Auditorium> auditoriumMap = new TreeMap<>();
            resSet.forEach((key, value) -> {
                try {
                    auditoriumMap.put(key, auditoriumDAO.getByName(value).get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            event.setAuditoriums(auditoriumMap);
        });
        return Optional.of(events.get(0));
    }

    @Nonnull
    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    //todo split method
    public Optional<Event> getById(@Nonnull Long id) {
        List<Event> events = jdbcTemplate.query("SELECT * FROM events WHERE events.id=?", EVENT_ROW_MAPPER, id);
        events.forEach(event -> {
            Map<LocalDateTime, String> resSet = jdbcTemplate.query("SELECT event_date, auditorium_name FROM dates WHERE dates.event_id=?", new ResultSetExtractor<Map<LocalDateTime, String>>() {
                @Override
                public Map<LocalDateTime, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    Map<LocalDateTime, String> mapRet = new HashMap<>();
                    while (rs.next()) {
                        mapRet.put(rs.getTimestamp("event_date").toLocalDateTime(), rs.getString("auditorium_name"));
                    }
                    return mapRet;
                }
            }, event.getId());
            event.setAirDates(new TreeSet<>(resSet.keySet()));

            NavigableMap<LocalDateTime, Auditorium> auditoriumMap = new TreeMap<>();
            resSet.forEach((key, value) -> {
                try {
                    auditoriumMap.put(key, auditoriumDAO.getByName(value).get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            event.setAuditoriums(auditoriumMap);
        });
        return Optional.of(events.get(0));
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return null;
    }

    @Override
    public Event save(@Nonnull Event object) {
        return null;
    }

    @Override
    public void remove(@Nonnull Event object) {

    }
}
