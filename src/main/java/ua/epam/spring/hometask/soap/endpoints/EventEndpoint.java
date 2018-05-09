package ua.epam.spring.hometask.soap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ua.epam.spring.hometask.service.event.EventService;
import ua.epam.spring.hometask.soap.messages.*;

import java.util.ArrayList;

@Endpoint
public class EventEndpoint {
    private static final String NAMESPACE_URI = "http://epam.com/soap";

    private EventService eventService;

    @Autowired
    public EventEndpoint(EventService eventService) {
        this.eventService = eventService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEventsRequest")
    @ResponsePayload
    public GetAllEventsResponse getAllEvents(@RequestPayload GetAllEventsRequest request){
        GetAllEventsResponse response = new GetAllEventsResponse();
        response.setEvents(new ArrayList(eventService.getAll()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEventByIdRequest")
    @ResponsePayload
    public GetEventByIdResponse getEventById(@RequestPayload GetEventByIdRequest request){
        GetEventByIdResponse response = new GetEventByIdResponse();
        response.setEvent(eventService.getById(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEventRequest")
    @ResponsePayload
    public AddEventResponse addEvent(@RequestPayload AddEventRequest request){
        AddEventResponse response = new AddEventResponse();
        eventService.save(request.getEvent());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEventRequest")
    @ResponsePayload
    public DeleteEventResponse deleteEvent(@RequestPayload DeleteEventRequest request){
        DeleteEventResponse response = new DeleteEventResponse();
        eventService.remove(eventService.getById(request.getId()));
        return response;
    }
}
