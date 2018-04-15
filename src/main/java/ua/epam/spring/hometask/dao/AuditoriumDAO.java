package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface AuditoriumDAO{

    Optional<Auditorium> getByName(String name) throws IOException;

    @Nonnull Collection<Auditorium> getAll() throws IOException;
}
