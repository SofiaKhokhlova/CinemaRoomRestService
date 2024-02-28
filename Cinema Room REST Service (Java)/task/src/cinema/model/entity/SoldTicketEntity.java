package cinema.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter@Setter@NoArgsConstructor
@Builder@AllArgsConstructor
@Entity
@Table(name = "tikets")
public class SoldTicketEntity {
    @Id
    String token;
    @Column(name="i_row")
    int row;
    @Column(name="i_column")
    int column;
    int price;
}
