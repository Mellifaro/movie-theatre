package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.event.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public String getAllUsers(ModelMap modelMap){
        List<Event> users = (List<Event>)eventService.getAll();
        modelMap.put("events", users);
        return "events";
    }

    @GetMapping(value = "/{id}/datetime/{datetime}")
    public String getEventByIdAndDate(ModelMap modelMap, @PathVariable("id") long id, @PathVariable("datetime") String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eventTime = LocalDateTime.parse(dateTime, formatter);
        return "eventInfo";
    }
}
