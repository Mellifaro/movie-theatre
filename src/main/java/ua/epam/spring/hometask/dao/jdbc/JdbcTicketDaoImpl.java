package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.util.LocalDateFormatter;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTicketDaoImpl implements TicketDAO {

    private static final RowMapper<Ticket> TICKET_ROW_MAPPER = new TicketRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertTicket;

    @Autowired
    public JdbcTicketDaoImpl(DataSource dataSource){
        insertTicket = new SimpleJdbcInsert(dataSource)
                .withTableName("tickets")
                .usingGeneratedKeyColumns("id");
    }

    @Nonnull
    @Override
    public NavigableSet<Ticket> getTicketsByUserId(Long userId) {
        List<Ticket> tickets = jdbcTemplate.query("SELECT * FROM tickets WHERE tickets.user_id=?", TICKET_ROW_MAPPER, userId);
        return new TreeSet<>(tickets);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Long eventId, LocalDateTime dateTime) {
        List<Ticket> tickets = jdbcTemplate.query("SELECT * FROM tickets WHERE tickets.event_id=? AND tickets.date_time=?", TICKET_ROW_MAPPER, eventId, dateTime);
        return new TreeSet<>(tickets);
    }

    @Override
    public Optional<Ticket> getById(@Nonnull Long id) {
        Ticket ticket = jdbcTemplate.queryForObject("SELECT * FROM tickets WHERE tickets.id=?", TICKET_ROW_MAPPER, id);
        return Optional.ofNullable(ticket);
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return jdbcTemplate.query("SELECT * FROM tickets", TICKET_ROW_MAPPER);
    }

    @Override
    public Ticket save(@Nonnull Ticket ticket) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("user_id", ticket.getUserId())
                .addValue("event_id", ticket.getEventId())
                .addValue("date_time", Timestamp.valueOf(ticket.getDateTime()))
                .addValue("seat", ticket.getSeat())
                .addValue("price", ticket.getPrice())
                .addValue("discount", ticket.getDiscount())
                .addValue("discount_type", ticket.getDiscountType().toString())
                .addValue("booking_date_time", Timestamp.valueOf(ticket.getBookingDateTime()));

        if(ticket.isNew()){
            Number newId = insertTicket.executeAndReturnKey(map);
            ticket.setId(newId.longValue());
        }else{
            namedParameterJdbcTemplate.update("UPDATE tickets SET user_id=:user_id, event_id=:event_id, " +
                    "date_time=:date_time, seat=:seat, price=:price, discount=:discount," +
                    "discount_type=:discount_type, booking_date_time=:booking_date_time WHERE id=:id", map);
        }
        return ticket;
    }

    @Override
    public void remove(@Nonnull Ticket ticket) {
        jdbcTemplate.update("DELETE FROM tickets WHERE tickets.id=?", ticket.getId());
    }

    static class TicketRowMapper implements RowMapper<Ticket>{

        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setUserId(rs.getLong("user_id"));
            ticket.setEventId(rs.getLong("event_id"));
            ticket.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
            ticket.setSeat(rs.getLong("seat"));
            ticket.setPrice(rs.getDouble("price"));
            ticket.setDiscount(rs.getInt("discount"));
            ticket.setDiscountType(DiscountType.valueOf(rs.getString("discount_type")));
            ticket.setBookingDateTime(rs.getTimestamp("booking_date_time").toLocalDateTime());
            return ticket;
        }
    }
}
