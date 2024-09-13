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
@Table(name = "withdrawals_transaction")
public class WithdrawalTransactionEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "atm_bank_id", referencedColumnName = "id", nullable = false)
    private CitadEntity atmBank;

}
