package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.EventInfoDao;
import ua.epam.spring.hometask.domain.EventInfo;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class JdbcEventInfoDaoImpl implements EventInfoDao {
    private static final BeanPropertyRowMapper<EventInfo> ROW_MAPPER = BeanPropertyRowMapper.newInstance(EventInfo.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertEventInfo;

    @Autowired
    public JdbcEventInfoDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertEventInfo = new SimpleJdbcInsert(dataSource)
                .withTableName("event_info");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<EventInfo> getByEventId(@NonNull Long eventId) {
        try {
            EventInfo eventInfo = jdbcTemplate.queryForObject("SELECT * FROM event_info WHERE event_id=?", ROW_MAPPER, eventId);
            return Optional.of(eventInfo);
        }catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }

    @Override
    public EventInfo save(@NonNull EventInfo eventInfo) {
        EventInfo result = getByEventId(eventInfo.getEventId()).orElse(null);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("event_id", eventInfo.getEventId())
                .addValue("count_by_name", eventInfo.getCountByName())
                .addValue("count_price_queried", eventInfo.getCountPriceQueried())
                .addValue("count_tickets_booked", eventInfo.getCountTicketsBooked());
        if(result == null){
            insertEventInfo.execute(map);
        }else{
            namedParameterJdbcTemplate.update("UPDATE event_info SET count_by_name=:count_by_name, count_price_queried=:count_price_queried, " +
                    "count_tickets_booked=:count_tickets_booked WHERE event_id=:event_id", map);
        }
        return eventInfo;
    }

    @Override
    public void remove(@NonNull Long eventId) {
        jdbcTemplate.update("DELETE FROM event_info WHERE event_id=?", eventId);
    }
}
