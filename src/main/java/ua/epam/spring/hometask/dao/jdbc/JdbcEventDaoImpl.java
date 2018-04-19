package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
    public Optional<Event> getByName(String name) {
        List<Event> events = jdbcTemplate.query("SELECT * FROM events WHERE events.name=?", EVENT_ROW_MAPPER, name);
        events.forEach(this::insertAirDatesAndAuditoriums);
        return Optional.of(events.get(0));
    }

    @Nonnull
    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        List<Event> events = jdbcTemplate.query("SELECT DISTINCT * FROM events INNER JOIN dates ON events.id = dates.event_id " +
                "WHERE dates.event_date BETWEEN  ? AND ?", EVENT_ROW_MAPPER, from, to);
        events.forEach(this::insertAirDatesAndAuditoriums);
        return events;
    }

    @Override
    public Optional<Event> getById(@Nonnull Long id) {
        List<Event> events = jdbcTemplate.query("SELECT * FROM events WHERE events.id=?", EVENT_ROW_MAPPER, id);
        events.forEach(this::insertAirDatesAndAuditoriums);
        return Optional.of(events.get(0));
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        List<Event> events = jdbcTemplate.query("SELECT * FROM events", EVENT_ROW_MAPPER);
        events.forEach(this::insertAirDatesAndAuditoriums);
        return events;
    }

    @Override
    public Event save(@Nonnull Event event) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", event.getId())
                .addValue("name", event.getName())
                .addValue("base_price", event.getBasePrice())
                .addValue("rating", event.getRating());
        if(event.isNew()){
            Number newId = insertEvent.executeAndReturnKey(map);
            event.setId(newId.longValue());
        }else{
            namedParameterJdbcTemplate.update("UPDATE events SET name=:name, base_price=:base_price, " +
                    "rating=:rating WHERE id=:id", map);
        }
        saveAirDatesAndAuditorims(event);
        return event;
    }

    @Override
    public void remove(@Nonnull Event event) {
        jdbcTemplate.update("DELETE FROM events WHERE events.id = ?", event.getId());
    }

    private void insertAirDatesAndAuditoriums(Event event){
        NavigableMap<LocalDateTime, Auditorium> dateAuditoriums = jdbcTemplate.query("SELECT event_date, auditorium_name FROM dates WHERE dates.event_id=?", new ResultSetExtractor<NavigableMap<LocalDateTime, Auditorium>>() {
            @Override
            public NavigableMap<LocalDateTime, Auditorium> extractData(ResultSet rs) throws SQLException, DataAccessException {
                NavigableMap<LocalDateTime, Auditorium> resSet = new TreeMap<>();
                while (rs.next()) {
                    try {
                        resSet.put(rs.getTimestamp("event_date").toLocalDateTime(),
                                   auditoriumDAO.getByName(rs.getString("auditorium_name")).get());
                    } catch (IOException e) {
                        e.printStackTrace();
                }
                }
                return resSet;
            }
        }, event.getId());
        event.setAuditoriums(dateAuditoriums);
        Objects.requireNonNull(dateAuditoriums);
        event.setAirDates(new TreeSet<>(dateAuditoriums.keySet()));
    }

    private void saveAirDatesAndAuditorims(Event event){
        NavigableMap<LocalDateTime, Auditorium> auditoriumMap = event.getAuditoriums();
        jdbcTemplate.update("DELETE * FROM dates WHERE dates.event_id=?", event.getId());
        auditoriumMap.forEach((date, auditorium) -> {
            jdbcTemplate.update("INSERT INTO dates(event_date, auditorium_name, event_id) VALUES (?, ?, ?)", date, auditorium.getName(), event.getId());
        });
    }
}
