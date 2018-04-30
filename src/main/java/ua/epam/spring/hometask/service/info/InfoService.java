package ua.epam.spring.hometask.service.info;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserDiscountInfo;

public interface InfoService {

    UserDiscountInfo getByUser(User user);
}
