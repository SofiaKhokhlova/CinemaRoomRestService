package cinema.configuration;

import cinema.model.Seat;
import cinema.repository.SeatRepository;;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitSeats implements ApplicationRunner {
    SeatRepository repository;
    CinemaProps props;

    @Override
    public void run(ApplicationArguments args){
        for (int i = 1; i <= props.total_rows(); i++) {
            for (int j = 1; j <= props.total_columns(); j++) {
                repository.save(new Seat(i, j));
            }
        }
    }
}
