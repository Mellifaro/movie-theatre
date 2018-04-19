package ua.epam.spring.hometask.domain;

/**
 * @author Viktor Skapoushchenko
 */
public class DomainObject {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return true if object id equals null
     */
    public boolean isNew(){
        return id == null;
    }
}
