package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Viktor Skapoushchenko
 */
@Repository
public class PropAuditoriumDaoImpl implements AuditoriumDAO {

    private final String SEATS_AMOUNT = ".seats.amount";
    private final String SEATS_VIP = ".seats.vip";
    private final String PROPS_PATH = "/auditorium.properties";
    private final String AUDITORIUM_NAMES = "auditorium.names";

    @Override
    public Optional<Auditorium> getByName(String name) throws IOException {
        Properties props = getDataFromProperties();
        return Optional.of(getAuditoriumByName(name, props));
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() throws IOException {
        Properties props = getDataFromProperties();
        String auditoriumNames = props.getProperty(AUDITORIUM_NAMES);
        if(StringUtils.isEmpty(auditoriumNames)){
            return Collections.emptySet();
        }

        List<String> names = Arrays.asList(auditoriumNames.split(","));
        return names.stream().map(name -> getAuditoriumByName(name, props)).collect(Collectors.toList());
    }

    private Properties getDataFromProperties() throws IOException {
        Properties props = new Properties();
        try(final InputStream stream = this.getClass().getResourceAsStream(PROPS_PATH)){
            props.load(stream);
        }
        return props;
    }

    private Set<Long> getVipSeats(String vip){
        return Arrays.stream(vip.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    private Auditorium getAuditoriumByName(String name, Properties props){
        List<String> names = Arrays.asList(props.getProperty(AUDITORIUM_NAMES).split(","));
        if(!names.contains(name)){
            throw new IllegalArgumentException("There is no auditorium with name: " + name);
        }

        Long numberOfSeats = Long.valueOf(props.getProperty(name + SEATS_AMOUNT));
        Set<Long> vipSeats = getVipSeats(props.getProperty(name + SEATS_VIP));
        return new Auditorium(name, numberOfSeats, vipSeats);
    }
}
