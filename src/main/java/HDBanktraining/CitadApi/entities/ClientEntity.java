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
@Table(name = "client")
public class ClientEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "dob")
    private String dob;
    @Column(name = "wallet")
    private double wallet;
    @OneToMany(mappedBy = "sender")
    private List<TransactionEntity> sentTransactions;
    @OneToMany(mappedBy = "receiver")
    private List<TransactionEntity> receivedTransactions;
    @ManyToOne
    @JoinColumn(name = "citad_id", referencedColumnName = "id")
    private CitadEntity citad;



    public static ClientEntity DefaultEntites(CitadEntity citad) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setCitad(citad);
        clientEntity.setAddress("address2");
        clientEntity.setEmail("email2");
        clientEntity.setName("name2");
        clientEntity.setPhone("phone2");
        clientEntity.setDob("dob");
        clientEntity.setWallet(0);
        return clientEntity;
    }
}
