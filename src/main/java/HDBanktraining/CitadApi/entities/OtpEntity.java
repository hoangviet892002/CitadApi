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
@Table(name = "otp")
public class OtpEntity extends BaseEntity{

    @Column(name = "otp")
    private String otp;

    @OneToOne
    @JoinColumn(name = "transaction", referencedColumnName = "id")
    private TransactionEntity transaction;

}
