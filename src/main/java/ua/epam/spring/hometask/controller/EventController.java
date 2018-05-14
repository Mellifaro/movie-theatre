package ua.epam.spring.hometask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.dto.AuditoriumDTO;
import ua.epam.spring.hometask.dto.RemoveDateDTO;
import ua.epam.spring.hometask.service.auditorium.AuditoriumService;
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
    private AuditoriumService auditoriumService;

    @Autowired
    public EventController(EventService eventService, BookingFacade bookingFacade, ObjectMapper objectMapper, AuditoriumService auditoriumService) {
        this.eventService = eventService;
        this.bookingFacade = bookingFacade;
        this.objectMapper = objectMapper;
        this.auditoriumService = auditoriumService;
    }

    @GetMapping
    public String getAllEvents(ModelMap modelMap) throws IOException {
        List<Event> users = (List<Event>)eventService.getAll();
        modelMap.put("events", users);
        modelMap.put("auditoriums", auditoriumService.getAll());
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

    @GetMapping(value = "/{id}/date/{datetime}/visual")
    public String getEventByIdAndDateVisual(ModelMap modelMap, @PathVariable("id") long id, @PathVariable("datetime") String dateTime) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime eventTime = LocalDateTime.parse(dateTime, formatter);
        Event event = eventService.getById(id);
        Auditorium auditorium = event.getAuditoriums().get(eventTime);
        AuditoriumDTO auditoriumDTO = auditoriumService.getWithPurchasedSeats(auditorium, bookingFacade.getPurchasedTicketsForEvent(event, eventTime));
        modelMap.put("event", event);
        modelMap.put("eventTime", eventTime);
        modelMap.put("auditoriumDTO", auditoriumDTO);
        return "eventInfoVisual";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNewEvent(Event event){
        eventService.save(event);
        return "redirect:/events";
    }

    @PostMapping(value = "/date/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addDateToEvent(@RequestParam("eventTime") String eventTimeString,
                                 @RequestParam("auditorium") String auditoriumName,
                                 @RequestParam("eventId") Long eventId) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime eventTime = LocalDateTime.parse(eventTimeString, formatter);

        Event event = eventService.getById(eventId);
        Auditorium auditorium = auditoriumService.getByName(auditoriumName);
        event.addAirDateTime(eventTime, auditorium);
        eventService.save(event);
        return "redirect:/events";
    }

    //Post is used here because of the occured problems with DELETE method(409 code after redirect to /users)
    @PostMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable("id") long id){
        eventService.remove(eventService.getById(id));
        return "redirect:/events";
    }

    //Post is used here because of the occured problems with DELETE method(409 code after redirect to /users)
    @PostMapping(value = "/date/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deleteDateForEvent(@RequestBody RemoveDateDTO removeDateDTO){
        Event event = eventService.getById(removeDateDTO.getEventId());
        event.removeAirDateTime(removeDateDTO.getEventTime());
        eventService.save(event);
        eventService.save(event);
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
