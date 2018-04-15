package ua.epam.spring.hometask.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author Viktor Skapoushchenko
 */
public class Auditorium {

    private String name;
    private long numberOfSeats;
    private Set<Long> vipSeats = Collections.emptySet();

    public Auditorium() {
    }

    public Auditorium(String name, long numberOfSeats, Set<Long> vipSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>     *
     * @param seats Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(seat -> vipSeats.contains(seat)).count();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats+1).boxed().collect(Collectors.toSet());
    }

    public Set<Long> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Set<Long> vipSeats) {
        this.vipSeats = vipSeats;
    }
    
    public boolean hasVipSeat(Long seat){
        return vipSeats.contains(seat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auditorium that = (Auditorium) o;
        return numberOfSeats == that.numberOfSeats &&
                Objects.equals(name, that.name) &&
                Objects.equals(vipSeats, that.vipSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfSeats, vipSeats);
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "name='" + name + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", vipSeats=" + vipSeats +
                '}';
    }
}
