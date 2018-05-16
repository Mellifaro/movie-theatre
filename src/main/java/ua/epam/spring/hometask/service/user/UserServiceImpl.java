package ua.epam.spring.hometask.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.NotFoundException;
import ua.epam.spring.hometask.security.AuthorizedUser;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDAO userDAO;
    private TicketDAO ticketDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, TicketDAO ticketDAO) {
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
    }

    @Override
    @Transactional
    public User getById(@Nonnull Long id) {
        User user = userDAO.getById(id).orElseThrow(() -> new NotFoundException("There is no user with id: " + id));
        return insertTickets(user);
    }

    @Override
    @Transactional
    public User getUserByEmail(@Nonnull String email) {
        User user = userDAO.getUserByEmail(email).orElseThrow(() -> new NotFoundException("There is no user with email: " + email));
        return insertTickets(user);
    }

    @Nonnull
    @Override
    @Transactional
    public Collection<User> getAll() {
        Collection<User> users =  userDAO.getAll();
        users.forEach(this::insertTickets);
        return users;
    }

    /**
     * Save new or update existing user. Tickets are not updated.
     */
    @Override
    @Transactional
    public User save(@Nonnull User user) {
        Objects.requireNonNull(user);
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public void remove(@Nonnull User user) {
        Objects.requireNonNull(user);
        userDAO.remove(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userDAO.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + " is not found"));
        return new AuthorizedUser(user);
    }

    private User insertTickets(User user){
        Objects.requireNonNull(user);
        NavigableSet<Ticket> tickets = ticketDAO.getTicketsByUserId(user.getId());
        user.setTickets(tickets);
        return user;
    }
}
