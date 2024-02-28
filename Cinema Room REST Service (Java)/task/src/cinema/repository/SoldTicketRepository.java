package cinema.repository;

import cinema.mapper.Mapper;
import cinema.model.SoldTicket;
import cinema.model.entity.SoldTicketEntity;
import cinema.model.response.PricedSeat;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SoldTicketRepository {

    final SoldTicketEntityRepository repository;
    final Mapper mapper;

    public SoldTicket save(PricedSeat ticket){
        String token = UUID.randomUUID().toString();
        SoldTicket res = new SoldTicket(
                token,ticket);
        log.info("token = '{}' ticket = '{}'", token, ticket);

        SoldTicketEntity soldTicket = SoldTicketEntity.builder().row(ticket.row()).
                column(ticket.column()).price(ticket.price()).
                token(token).build();

        repository.save(soldTicket);
        return res;
    }

    public Optional<PricedSeat> remove(String token) {
        log.info("remove token = '{}'", token);
        var entity =  repository.findById(token);
        if(entity.isPresent())
            repository.delete(entity.get());
        return entity.map(mapper::toPricedSeat);
    }

    public int totalIncome(){
        return repository.sumPrices();
    }

    public int count() {
        return (int)repository.count();
    }
}
