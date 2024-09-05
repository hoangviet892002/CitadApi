package HDBanktraining.CitadApi.entities;

import jakarta.persistence.Column;

public class CitadEntity extends BaseEntity {

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "branch")
    private String branch;

}
