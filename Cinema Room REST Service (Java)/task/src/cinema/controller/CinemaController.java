package cinema.controller;
import cinema.configuration.CinemaProps;
import cinema.exception.WrongPasswordException;
import cinema.model.Seat;

import cinema.model.SoldTicket;
import cinema.model.request.TokenDTO;
import cinema.model.response.CinemaResponse;
import cinema.model.response.CinemaStatistics;
import cinema.model.response.ReturnedTicket;
import cinema.service.CinemaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
public class CinemaController{
    CinemaService cinemaService;
    CinemaProps cinemaProps;

    @GetMapping("/seats")
    CinemaResponse seats() {
        var seats = cinemaService.getAvailableSeats();

        return new CinemaResponse(cinemaProps.total_rows(), cinemaProps.total_columns(), seats);
    }

    @PostMapping("/purchase")
    SoldTicket purchase(
            @RequestBody Seat seat
    ){
        log.info("Purchase request for seat = {}",
                seat);
        return cinemaService.purchase(seat);
    }

    @PostMapping("/return")
    ReturnedTicket returnHandler(@RequestBody TokenDTO tokenDTO){
        return new ReturnedTicket(
                cinemaService.returnTicket(tokenDTO));
    }

    @PostMapping("/stats")
    CinemaStatistics statistics(@RequestParam Optional<String> password){
        password.filter(it -> cinemaProps.secret().equals(it)).orElseThrow( WrongPasswordException::new);
        return cinemaService.statistics();
    }
}