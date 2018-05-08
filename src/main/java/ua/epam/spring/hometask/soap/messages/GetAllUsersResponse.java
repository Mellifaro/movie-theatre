package ua.epam.spring.hometask.soap.messages;

import ua.epam.spring.hometask.domain.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "getAllUsersResponse")
public class GetAllUsersResponse {

    protected List<User> user;
    public List<User> getUser() {
        if (user == null) {
            user = new ArrayList<User>();
        }
        return this.user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
