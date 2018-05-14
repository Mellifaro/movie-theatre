package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.dto.BookingInfoDTO;
import ua.epam.spring.hometask.security.AuthorizedUser;
import ua.epam.spring.hometask.service.booking.BookingFacade;
import ua.epam.spring.hometask.service.event.EventService;
import ua.epam.spring.hometask.service.user.UserService;
import ua.epam.spring.hometask.util.SeatParsingUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
@RequestMapping(value = "/book")
public class BookingController {

    private EventService eventService;
    private UserService userService;
    private BookingFacade bookingFacade;

    @Autowired
    public BookingController(EventService eventService, UserService userService, BookingFacade bookingFacade) {
        this.eventService = eventService;
        this.userService = userService;
        this.bookingFacade = bookingFacade;
    }

    @PostMapping(value = "/event/{eventId}/datetime/{dateTime}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String bookTicketsFormForm(ModelMap modelMap, @RequestParam String seats, @PathVariable("eventId") Long eventId, @PathVariable("dateTime") String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime eventTime = LocalDateTime.parse(dateTime, formatter);
        Event event = eventService.getById(eventId);
        AuthorizedUser userDetails = (AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        bookingFacade.bookTickets(event, eventTime, user, SeatParsingUtils.parseString(seats));

        User updatedUser = userService.getUserByEmail(user.getEmail());
        userDetails.setBalance(updatedUser.getBalance());
        modelMap.put("user", updatedUser);

        return "redirect:/tickets";
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String bookTicketsFromJson(@RequestBody BookingInfoDTO bookingInfo){
        Event event = eventService.getById(bookingInfo.getEventId());
        AuthorizedUser userDetails = (AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        bookingFacade.bookTickets(event, bookingInfo.getDateTime(), user, bookingInfo.getSeats());

        User updatedUser = userService.getUserByEmail(user.getEmail());
        userDetails.setBalance(updatedUser.getBalance());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().put("user", updatedUser);
        return "redirect:/tickets";
    }
}
