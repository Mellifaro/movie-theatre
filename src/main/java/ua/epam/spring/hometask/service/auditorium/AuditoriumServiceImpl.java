package ua.epam.spring.hometask.service.auditorium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumDAO auditoriumDAO;

    @Autowired
    public AuditoriumServiceImpl(AuditoriumDAO auditoriumDAO) {
        this.auditoriumDAO = auditoriumDAO;
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() throws IOException {
        return auditoriumDAO.getAll();
    }

    @Override
    public Auditorium getByName(@Nonnull String name) throws IOException {
        return auditoriumDAO.getByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Auditorium with name: " + name + " not found"));
    }
}
