package ua.epam.spring.hometask.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.NotFoundException;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.NavigableSet;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private TicketDAO ticketDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, TicketDAO ticketDAO) {
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
    }

    @Override
    public User getById(@Nonnull Long id) {
        User user = userDAO.getById(id).orElseThrow(() -> new NotFoundException("There is no user with id: " + id));
        return insertTickets(user);
    }

    @Override
    public User getUserByEmail(@Nonnull String email) {
        User user = userDAO.getUserByEmail(email).orElseThrow(() -> new NotFoundException("There is no user with email: " + email));
        return insertTickets(user);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        Collection<User> users =  userDAO.getAll();
        users.forEach(this::insertTickets);
        return users;
    }

    /**
     * Save new or update existing user. Tickets are not updated.
     */
    @Override
    public User save(@Nonnull User user) {
        return userDAO.save(user);
    }

    @Override
    public void remove(@Nonnull User user) {
        userDAO.remove(user);
    }

    private User insertTickets(User user){
        NavigableSet<Ticket> tickets = ticketDAO.getTicketsByUserId(user.getId());
        user.setTickets(tickets);
        return user;
    }
}
