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
public class otpEntity extends BaseEntity{
    @Column(name = "otp")
    private String otp;
    @Column(name = "expired_at")
    private String expiredAt;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;
}
