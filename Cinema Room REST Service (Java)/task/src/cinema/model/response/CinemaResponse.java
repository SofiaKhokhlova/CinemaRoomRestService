package cinema.model.response;

import cinema.model.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;

public record CinemaResponse(
        int totalRows,
        int totalColumns,
        Collection<PricedSeat> availableSeats
) {
}