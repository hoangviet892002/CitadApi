package HDBanktraining.CitadApi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

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

}
