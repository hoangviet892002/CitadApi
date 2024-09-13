package HDBanktraining.CitadApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Table(name = "citad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CitadEntity extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "branch")
    private String branch;

    @OneToMany(mappedBy = "citad")
    private List<ClientEntity> clients;

    @OneToMany(mappedBy = "atmBank")
    private List<WithdrawalTransactionEntity> withdrawalTransactions;

    public CitadEntity(String code, String name, String branch) {
        this.code = code;
        this.name = name;
        this.branch = branch;
    }

}
