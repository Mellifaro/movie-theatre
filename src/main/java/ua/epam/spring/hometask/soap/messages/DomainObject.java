package ua.epam.spring.hometask.soap.messages;


import ua.epam.spring.hometask.domain.User;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "domainObject")
@XmlSeeAlso({
    User.class
})
public class DomainObject {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

}
