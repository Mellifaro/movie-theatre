package ua.epam.spring.hometask.controller.rest;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.event.EventService;

import java.util.Collection;

@RestController
@RequestMapping(value = "/rest/events")
public class EventRestController {

    private EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Event> getAllEventss(){
        return eventService.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getUserById(@PathVariable("id") Long id){
        return eventService.getById(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveEvent(@RequestBody Event event){
        eventService.save(event);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEvent(@PathVariable("id") Long id){
        eventService.remove(eventService.getById(id));
    }
}
