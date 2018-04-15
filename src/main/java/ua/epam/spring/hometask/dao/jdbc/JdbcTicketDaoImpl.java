package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Ticket;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTicketDaoImpl implements TicketDAO {

    private static final BeanPropertyRowMapper<Ticket> TICKET_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Ticket.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDao;

    private SimpleJdbcInsert insertEvent;

    @Autowired
    public JdbcTicketDaoImpl(DataSource dataSource){
        insertEvent = new SimpleJdbcInsert(dataSource)
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
        Ticket ticket = jdbcTemplate.query("SELECT * FROM tickets WHERE tickets.id=?", new ResultSetExtractor<Ticket>(){
            @Override
            public Ticket extractData(ResultSet rs) throws SQLException, DataAccessException {
                Ticket ticket = new Ticket();
                while (rs.next()) {
                    ticket.setId(rs.getLong("id"));
                    ticket.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                    ticket.setSeat(rs.getLong("seat"));
//                    ticket.setUser(userDAO.getById(rs.getLong("user_id")).get());
//                    ticket.setEvent(eventDao.getById(rs.getLong("event_id")).get());
                }

                return ticket;
            }
        }, id);
        return Optional.ofNullable(ticket);
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return null;
    }

    @Override
    public Ticket save(@Nonnull Ticket object) {
        return null;
    }

    @Override
    public void remove(@Nonnull Ticket object) {

    }
}
