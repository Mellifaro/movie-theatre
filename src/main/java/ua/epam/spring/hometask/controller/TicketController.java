package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.dto.TicketDTO;
import ua.epam.spring.hometask.security.AuthorizedUser;
import ua.epam.spring.hometask.service.ticket.TicketConverter;
import ua.epam.spring.hometask.service.user.UserService;

import java.util.Set;


@Controller
@RequestMapping(value = "/tickets")
public class TicketController {

    private UserService userService;
    private TicketConverter ticketConverter;

    @Autowired
    public TicketController(UserService userService, TicketConverter ticketConverter) {
        this.userService = userService;
        this.ticketConverter = ticketConverter;
    }

    @GetMapping
    public String getAllTickets(ModelMap modelMap){
        AuthorizedUser userDetails = (AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Set<TicketDTO> ticketsDTO = ticketConverter.getSetOfTicketDTO(user.getTickets());
        modelMap.put("tickets", ticketsDTO);
        return "tickets";
    }
}
