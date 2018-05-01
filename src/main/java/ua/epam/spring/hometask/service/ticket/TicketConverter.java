package ua.epam.spring.hometask.service.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.dto.TicketDTO;
import ua.epam.spring.hometask.service.event.EventService;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TicketConverter {
    private ConcurrentHashMap<Long, Event> eventMap = new ConcurrentHashMap<>();

    private EventService eventService;
    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Autowired
    public TicketConverter(EventService eventService) {
        this.eventService = eventService;
    }

    public Set<TicketDTO> getSetOfTicketDTO(Set<Ticket> tickets){
        eventMap.clear();
        eventService.getAll().forEach(event -> eventMap.put(event.getId(), event));
        return tickets.stream()
                .map(this::getDTOFromTicket)
                .collect(Collectors.toSet());
    }

    private TicketDTO getDTOFromTicket(Ticket ticket){
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setUserId(ticket.getUserId());
        Event event = eventMap.get(ticket.getEventId());
        ticketDTO.setEvent(event);
        ticketDTO.setDateTime(ticket.getDateTime().format(FORMATTER));
        ticketDTO.setAuditorium(event.getAuditoriums().get(ticket.getDateTime()));
        ticketDTO.setSeat(ticket.getSeat());
        ticketDTO.setPrice(ticket.getPrice());
        ticketDTO.setDiscount(ticket.getDiscount());
        ticketDTO.setDiscountType(ticket.getDiscountType());
        ticketDTO.setBookingDateTime(ticket.getBookingDateTime().format(FORMATTER));
        return ticketDTO;
    }
}
