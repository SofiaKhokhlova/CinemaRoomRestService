package cinema.service;

import cinema.configuration.CinemaProps;
import cinema.exception.AlreadyPurchasedException;
import cinema.exception.OutOfBoundsCoordinatesException;
import cinema.exception.WrongTokenException;
import cinema.mapper.Mapper;
import cinema.model.Seat;
import cinema.model.SoldTicket;
import cinema.model.request.TokenDTO;
import cinema.model.response.CinemaStatistics;
import cinema.model.response.PricedSeat;
import cinema.repository.SeatRepository;
import cinema.repository.SoldTicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CinemaService {
    SeatRepository seatRepository;
    SoldTicketRepository ticketRepository;
    CinemaProps props;
    Mapper mapper;

    public List<PricedSeat> getAvailableSeats(){
        return seatRepository.getAvailableSeats()
                .stream()
                .map(seat -> addPrice(seat))
                .toList();
    }

    private int calcPrice(Seat seat){
        return seat.row() <= props.numberOfFirstRows()
                ? props.priceForBottomRows()
                : props.priceForTopRows();
    }

    private PricedSeat addPrice(Seat seat){
        return PricedSeat.builder()
                .price(calcPrice(seat))
                .row(seat.row())
                .column(seat.column())
                .build();
    }

    public SoldTicket purchase(Seat seat) {
        if(illegalCoordinate(seat)){
            throw new OutOfBoundsCoordinatesException();
        }
        if(!seatRepository.contains(seat)){
            throw new AlreadyPurchasedException();
        }
        seatRepository.remove(seat);
        PricedSeat ticket = addPrice(seat);
        return ticketRepository.save(ticket);
    }

    private boolean illegalCoordinate(Seat seat){
        return !(0 < seat.column() && seat.column() <= props.total_columns() &&
                0 < seat.row() && seat.row() <= props.total_rows());
    }

    public PricedSeat returnTicket(TokenDTO tokenDTO) {
        var pricedSeat = ticketRepository.remove(tokenDTO.token())
                .orElseThrow(WrongTokenException::new);
        seatRepository.save(mapper.toSeat(pricedSeat));
        return pricedSeat;
    }

    public CinemaStatistics statistics() {
        return CinemaStatistics.builder().
                currentIncome(ticketRepository.totalIncome()).
                numberOfAvailableSeats(seatRepository.count()).
                numberOfPurchasedTickets(ticketRepository.count()).
                build();
    }
}