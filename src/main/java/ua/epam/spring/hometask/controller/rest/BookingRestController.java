package ua.epam.spring.hometask.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.controller.rest.requests.BookingTciketsRequest;
import ua.epam.spring.hometask.service.booking.BookingFacade;
import ua.epam.spring.hometask.service.event.EventService;
import ua.epam.spring.hometask.service.user.UserService;
import ua.epam.spring.hometask.util.SeatParsingUtils;

import java.util.Set;

@RestController
@RequestMapping(value = "/rest/book")
public class BookingRestController {

    private UserService userService;
    private EventService eventService;
    private BookingFacade bookingFacade;


    @Autowired
    public BookingRestController(UserService userService, EventService eventService, BookingFacade bookingFacade) {
        this.userService = userService;
        this.eventService = eventService;
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Ticket> bookTickets(@RequestBody BookingTciketsRequest request){
        Event event = eventService.getById(request.getEventId());
        Set<Long> listSeats = SeatParsingUtils.parseString(request.getSeats());
        User user = userService.getById(request.getUserId());
        return bookingFacade.bookTickets(event, request.getDateTime(), user, listSeats);
    }
}
