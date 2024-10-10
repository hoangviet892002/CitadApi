package HDBanktraining.CitadApi.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "transfer_napas")
public class TransferNapasEntity extends BaseEntity{
    @OneToOne
    @JoinColumn(name="transaction_id", referencedColumnName = "id", nullable = false)
    private TransactionEntity transaction;

    @Column(name = "receiver_bank")
    private String receiverBank;

    @Column(name = "receiver_account")
    private String receiverAccount;
}
