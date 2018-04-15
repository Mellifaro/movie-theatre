package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.util.LocalDateFormatter;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JdbcUserDaoImpl implements UserDAO {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserDaoImpl(DataSource dataSource){
        insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> getById(@Nonnull Long id) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE users.id=?", ROW_MAPPER, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE users.email=?", ROW_MAPPER, email);
        return Optional.ofNullable(user);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", ROW_MAPPER);
    }

    @Override
    public User save(@Nonnull User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("birthday", LocalDateFormatter.convertFromLocalDate(user.getBirthday()));

        if(user.isNew()){
            Number newId = insertUser.executeAndReturnKey(map);
            user.setId(newId.longValue());
        }else{
            namedParameterJdbcTemplate.update("UPDATE users SET first_name=:first_name, last_name=:last_name, " +
                    "email=:email, birthday=:birthday WHERE id=:id", map);
        }
        return user;
    }

    @Override
    public void remove(@Nonnull User user) {
        jdbcTemplate.update("DELETE FROM users WHERE users.id=?", user.getId());
    }
}
