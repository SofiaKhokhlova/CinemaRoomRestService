package cinema.model.response;

import lombok.Builder;

@Builder
public record CinemaStatistics(
        int currentIncome,
        int numberOfAvailableSeats,
        int numberOfPurchasedTickets
) {
}