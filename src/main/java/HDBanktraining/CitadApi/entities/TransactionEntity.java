package HDBanktraining.CitadApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity{

    @Column(name = "amount")
    private double amount;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private ClientEntity receiver;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private ClientEntity sender;


}
