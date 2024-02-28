package cinema.repository;

import cinema.model.Seat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SeatRepository {
    List<Seat> data = new ArrayList<>();

    public void save(Seat seat){
        data.add(seat);
    }

    public List<Seat> getAvailableSeats(){
        return Collections.unmodifiableList(data);
    }

    public void remove(Seat seat){
        data.remove(seat);
    }

    public int count(){
        return data.size();
    }

    public boolean contains(Seat seat){
        return data.contains(seat);
    }
}
