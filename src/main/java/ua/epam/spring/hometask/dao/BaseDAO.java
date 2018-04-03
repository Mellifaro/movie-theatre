package ua.epam.spring.hometask.dao;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface BaseDAO<T> {

    T getById(@Nonnull Long id);

    @Nonnull
    Collection<T> getAll();

    T save(@Nonnull T object);

    void remove(@Nonnull T object);
}
