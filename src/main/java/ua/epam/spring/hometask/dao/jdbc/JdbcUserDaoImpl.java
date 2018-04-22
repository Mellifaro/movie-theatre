package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.util.LocalDateFormatter;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.util.*;

/**
 * @author Viktor Skapoushchenko
 */
@Repository
public class JdbcUserDaoImpl implements UserDAO {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private TicketDAO ticketDAO;
    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserDaoImpl(DataSource dataSource){
        insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> getById(@Nonnull Long id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE users.id=?", ROW_MAPPER, id);
            insertTickets(user);
            return Optional.of(user);
        }catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE users.email=?", ROW_MAPPER, email);
            insertTickets(user);
            return Optional.of(user);
        }catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", ROW_MAPPER);
        users.forEach(this::insertTickets);
        return users;
    }

    @Override
    public User save(@Nonnull User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("birthday", LocalDateFormatter.convertToTimestamp(user.getBirthday()));

        if(user.isNew()){
            Number newId = insertUser.executeAndReturnKey(map);
            user.setId(newId.longValue());
        }else{
            namedParameterJdbcTemplate.update("UPDATE users SET first_name=:first_name, last_name=:last_name, " +
                    "email=:email, birthday=:birthday WHERE id=:id", map);
        }
        updateTicketsToDb(user);
        return user;
    }

    @Override
    public void remove(@Nonnull User user) {
        jdbcTemplate.update("DELETE FROM users WHERE users.id=?", user.getId());
    }

    private void insertTickets(@Nonnull User user){
        NavigableSet<Ticket> tickets = ticketDAO.getTicketsByUserId(user.getId());
        user.setTickets(tickets);
    }

    private void updateTicketsToDb(@Nonnull User user) {
        NavigableSet<Ticket> existingTickets = ticketDAO.getTicketsByUserId(user.getId());
        NavigableSet<Ticket> ticketsToUpdate = user.getTickets();

        existingTickets.stream().filter(existingTicket -> {
            return ticketsToUpdate.stream()
                    .allMatch(ticketToUpdate -> !existingTicket.getId().equals(ticketToUpdate.getId()));

        }).forEach(ticketDAO::remove);
        ticketsToUpdate.forEach(ticketDAO::save);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }
}
