package ua.epam.spring.hometask.service.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.UserDiscountInfoDAO;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserDiscountInfo;

import java.util.Objects;

@Service
public class InfoServiceImpl implements InfoService{

    private UserDiscountInfoDAO userDiscountInfoDAO;

    @Autowired
    public InfoServiceImpl(UserDiscountInfoDAO userDiscountInfoDAO) {
        this.userDiscountInfoDAO = userDiscountInfoDAO;
    }

    @Override
    @Transactional
    public UserDiscountInfo getByUser(User user) {
        Objects.requireNonNull(user);
        return userDiscountInfoDAO.getByUserId(user.getId()).orElse(new UserDiscountInfo(user.getId()));
    }
}
