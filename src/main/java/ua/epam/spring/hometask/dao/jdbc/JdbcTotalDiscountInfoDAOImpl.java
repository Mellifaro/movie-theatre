package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.TotalDiscountInfoDAO;
import ua.epam.spring.hometask.domain.TotalDiscountInfo;


@Repository
public class JdbcTotalDiscountInfoDAOImpl implements TotalDiscountInfoDAO {
    private static final BeanPropertyRowMapper<TotalDiscountInfo> ROW_MAPPER = BeanPropertyRowMapper.newInstance(TotalDiscountInfo.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTotalDiscountInfoDAOImpl() {
    }

    @Override
    public TotalDiscountInfo getByDiscountName(String discountName) {
        TotalDiscountInfo discountInfo = jdbcTemplate.queryForObject("SELECT * FROM total_discounts " +
                "WHERE total_discounts.discount_type=?", ROW_MAPPER, discountName);
        return discountInfo;
    }

    @Override
    public TotalDiscountInfo save(TotalDiscountInfo discountInfo) {
        TotalDiscountInfo result = getByDiscountName(discountInfo.getDiscountType().name());
        if(result == null){
            jdbcTemplate.update("INSERT INTO total_discounts(discount_type, amount) " +
                    "VALUES (?, ?)", discountInfo.getDiscountType(), discountInfo.getAmount());
        }else{
            jdbcTemplate.update("UPDATE total_discounts SET total_discounts.amount=?", discountInfo.getAmount());
        }
        return result;
    }

    @Override
    public void remove(String discountName) {
        jdbcTemplate.update("DELETE FROM total_discounts WHERE total_discounts.discount_type=?", discountName);
    }
}
