package ua.epam.spring.hometask.soap.messages;

import ua.epam.spring.hometask.domain.Event;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "addEventRequest")
public class AddEventRequest {

    @XmlElement(required = true)
    protected Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
