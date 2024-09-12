package HDBanktraining.CitadApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "withdrawals_transaction")
public class WithdrawalTransactionEntity extends TransactionEntity {

    @ManyToOne
    @JoinColumn(name = "atm_bank_id", referencedColumnName = "id", nullable = false)
    private CitadEntity atmBank;

}
