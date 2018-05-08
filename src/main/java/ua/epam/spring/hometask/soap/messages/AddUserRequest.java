
package ua.epam.spring.hometask.soap.messages;

import ua.epam.spring.hometask.domain.User;
import javax.xml.bind.annotation.*;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "addUserRequest")
public class AddUserRequest {

    @XmlElement(required = true)
    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User value) {
        this.user = value;
    }

}
