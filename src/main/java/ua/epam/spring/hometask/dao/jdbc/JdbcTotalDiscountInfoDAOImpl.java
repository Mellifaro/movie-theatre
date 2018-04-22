package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.TotalDiscountInfoDAO;
import ua.epam.spring.hometask.domain.TotalDiscountInfo;

import java.util.Optional;

/**
 * @author Viktor Skapoushchenko
 */
@Repository
public class JdbcTotalDiscountInfoDAOImpl implements TotalDiscountInfoDAO {
    private static final BeanPropertyRowMapper<TotalDiscountInfo> ROW_MAPPER = BeanPropertyRowMapper.newInstance(TotalDiscountInfo.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TotalDiscountInfo> getByDiscountName(String discountName) {
        try {
            TotalDiscountInfo discountInfo = jdbcTemplate.queryForObject("SELECT * FROM total_discounts " +
                    "WHERE discount_type=?", ROW_MAPPER, discountName);
            return Optional.of(discountInfo);
        }catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }

    @Override
    public TotalDiscountInfo save(TotalDiscountInfo discountInfo) {
        TotalDiscountInfo result = getByDiscountName(discountInfo.getDiscountType().name()).orElseGet(null);
        if(result == null){
            jdbcTemplate.update("INSERT INTO total_discounts(discount_type, amount) " +
                    "VALUES (?, ?)", discountInfo.getDiscountType().toString(), discountInfo.getAmount());
        }else{
            jdbcTemplate.update("UPDATE total_discounts SET amount=? WHERE discount_type=?", discountInfo.getAmount(), discountInfo.getDiscountType().toString());
        }
        return result;
    }

    @Override
    public void remove(String discountName) {
        jdbcTemplate.update("DELETE FROM total_discounts WHERE discount_type=?", discountName);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
