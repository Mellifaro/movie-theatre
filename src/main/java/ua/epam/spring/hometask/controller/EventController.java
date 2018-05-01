package ua.epam.spring.hometask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.booking.BookingFacade;
import ua.epam.spring.hometask.service.event.EventService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
@RequestMapping(value = "/events")
public class EventController {

    private EventService eventService;
    private BookingFacade bookingFacade;
    private ObjectMapper objectMapper;

    @Autowired
    public EventController(EventService eventService, BookingFacade bookingFacade, ObjectMapper objectMapper) {
        this.eventService = eventService;
        this.bookingFacade = bookingFacade;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String getAllUsers(ModelMap modelMap){
        List<Event> users = (List<Event>)eventService.getAll();
        modelMap.put("events", users);
        return "events";
    }

    @GetMapping(value = "/{id}/date/{datetime}")
    public String getEventByIdAndDate(ModelMap modelMap, @PathVariable("id") long id, @PathVariable("datetime") String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime eventTime = LocalDateTime.parse(dateTime, formatter);
        Event event = eventService.getById(id);
        Auditorium auditorium = event.getAuditoriums().get(eventTime);
        Set<Long> allAvailableSeats = bookingFacade.getAllAvailableSeatsForEvent(event, eventTime);
        modelMap.put("event", event);
        modelMap.put("eventTime", eventTime);
        modelMap.put("auditorium", auditorium);
        modelMap.put("simpleSeats", bookingFacade.getAvailableSimpleSeats(allAvailableSeats, auditorium));
        modelMap.put("vipSeats", bookingFacade.getAvailableVIPSeats(allAvailableSeats, auditorium));
        return "eventInfo";
    }

    @PostMapping(value = "/uploadFile")
    public String uploadMultipleFileHandler(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<Event> eventList = objectMapper.readValue(file.getBytes(), typeFactory.constructCollectionType(List.class, Event.class));
            eventList.forEach(event -> eventService.save(event));
            return "redirect:/events";
        }
        return "redirect:/error";
    }

}
