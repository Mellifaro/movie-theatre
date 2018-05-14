package ua.epam.spring.hometask.service.auditorium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.dto.AuditoriumDTO;
import ua.epam.spring.hometask.dto.Seat;
import ua.epam.spring.hometask.dto.SeatStatus;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public AuditoriumDTO getWithPurchasedSeats(Auditorium auditorium, Set<Ticket> purchasedTickets) throws IOException{
        Set<Long> purchasedSeats =  purchasedTickets.stream()
                .mapToLong(Ticket::getSeat)
                .boxed()
                .collect(Collectors.toSet());

        List<List<Seat>> seatMatrix = auditoriumDAO.getSeatMatrix(auditorium.getName());
        AuditoriumDTO auditoriumDTO = new AuditoriumDTO(auditorium.getName(), seatMatrix);
        auditoriumDTO.getSeatMatrix().forEach(raw -> raw.forEach(seat -> {
            if(purchasedSeats.contains(seat.getSeat())){
                seat.setCurrentStatus(SeatStatus.PURCHASED);
            }
        }));
        return auditoriumDTO;
    }
}
