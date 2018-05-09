package ua.epam.spring.hometask.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.dto.TicketDTO;
import ua.epam.spring.hometask.service.ticket.TicketConverter;
import ua.epam.spring.hometask.service.user.UserService;

import java.util.Set;

@RestController
@RequestMapping(value = "/rest/tickets")
public class TicketRestController {

    private UserService userService;
    private TicketConverter ticketConverter;

    @Autowired
    public TicketRestController(UserService userService, TicketConverter ticketConverter) {
        this.userService = userService;
        this.ticketConverter = ticketConverter;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public Set<TicketDTO> getAllTicketsForUser(@PathVariable("userId") Long userId){
        Set<Ticket> tickets = userService.getById(userId).getTickets();
        return ticketConverter.getSetOfTicketDTO(tickets);
    }
}
