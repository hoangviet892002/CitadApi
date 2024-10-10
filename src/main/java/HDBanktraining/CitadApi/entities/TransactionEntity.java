package HDBanktraining.CitadApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity{

    @Column(name = "amount", nullable = false)
    private double amount;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;
    @Column(name = "type")
    private String type;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private ClientEntity sender;

}
