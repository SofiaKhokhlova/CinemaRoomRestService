package cinema.repository;

import cinema.model.entity.SoldTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SoldTicketEntityRepository extends JpaRepository<SoldTicketEntity, String> {
    @Query("select SUM(s.price) from SoldTicketEntity s")
    int sumPrices();
}
