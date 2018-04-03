package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface AuditoriumDAO{

    Auditorium getByName(String name);

    @Nonnull Collection<Auditorium> getAll();
}
