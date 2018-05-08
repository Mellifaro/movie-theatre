package ua.epam.spring.hometask.soap.messages;


import ua.epam.spring.hometask.domain.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "getUserByIdResponse")
public class GetUserByIdResponse {

    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User value) {
        this.user = value;
    }

}
