package HDBanktraining.CitadApi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "client")
public class ClientEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name="number")
    private String number;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private String dob;

    @Column(name = "wallet")
    private double wallet;

    @ManyToOne
    @JoinColumn(name = "citad_id", referencedColumnName = "id", nullable = false)
    private CitadEntity citad;

    @OneToMany(mappedBy = "sender")
    @JsonBackReference
    private List<TransactionEntity> sentTransfers;

    @OneToMany(mappedBy = "receiver")
    private List<TransferTransactionEntity> receivedTransfers;

    public static ClientEntity DefaultEntites(CitadEntity citad) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setCitad(citad);
        clientEntity.setAddress("address");
        clientEntity.setEmail("email");
        clientEntity.setName("viet");
        clientEntity.setPhone("phone");
        clientEntity.setDob("dob");
        clientEntity.setNumber("viet");
        clientEntity.setWallet(0);
        return clientEntity;
    }
}
