package ua.epam.spring.hometask.dao.jdbc;

import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.UserDiscountInfoDAO;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.UserDiscountInfo;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Viktor Skapoushchenko
 */
@Repository
public class JdbcUserDiscountInfoDAOImpl implements UserDiscountInfoDAO {

    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Optional<UserDiscountInfo> getByUserId(@Nonnull Long userId) {
        UserDiscountInfo userDiscountInfo = new UserDiscountInfo();
        userDiscountInfo.setUserId(userId);
        insertDiscountMap(userDiscountInfo);
        return Optional.of(userDiscountInfo);
    }

    @Override
    @Transactional
    public UserDiscountInfo save(@Nonnull UserDiscountInfo userDiscountInfo) {
        Long userId = userDiscountInfo.getUserId();
        jdbcTemplate.update("DELETE FROM user_discounts WHERE user_id=?", userId);
        userDiscountInfo.getDiscountMap().forEach(((discountName, amount) -> {
            jdbcTemplate.update("INSERT INTO user_discounts(user_id, discount_type, amount) " +
                    "VALUES (?,?,?)", userId, discountName.toString(), amount);
        }));
        return userDiscountInfo;
    }

    @Override
    @Transactional
    public void remove(@Nonnull Long userId, @Nonnull String discountType) {
        jdbcTemplate.update("DELETE FROM user_discounts WHERE user_discounts.user_id=? " +
                "AND user_discounts.discount_type=?", userId, discountType);
    }

    @Transactional
    private void insertDiscountMap(UserDiscountInfo discountInfo){
        Map<DiscountType, Integer> discountMap = jdbcTemplate.query("SELECT discount_type, amount FROM user_discounts WHERE user_id=?", new ResultSetExtractor<Map<DiscountType, Integer>>() {
            @Override
            public Map<DiscountType, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<DiscountType, Integer> resSet = new HashMap<>();
                while (rs.next()) {
                    resSet.put(DiscountType.valueOf(rs.getString("discount_type")),
                            rs.getInt("amount"));

                }
                return resSet;
            }
        }, discountInfo.getUserId());
        discountInfo.setDiscountMap(discountMap);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
