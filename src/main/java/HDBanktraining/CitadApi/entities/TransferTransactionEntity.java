package HDBanktraining.CitadApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfer_transaction")
public class TransferTransactionEntity extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private ClientEntity receiver;

    @OneToOne
    @JoinColumn(name="transaction_id", referencedColumnName = "id", nullable = false)
    private TransactionEntity transaction;



}
