package ua.epam.spring.hometask.service.user;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;

/**
 * @author Viktor Skapoushchenko
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * @param email Email of the user
     * @return found user or <code>null</code>
     */
    User getUserByEmail(@Nonnull String email);

}
