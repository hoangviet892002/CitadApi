package HDBanktraining.CitadApi.entities;

import jakarta.persistence.Id;

public class BaseEntity {
    @Id
    private String id;
    private String createdAt;
    private String updatedAt;
    private boolean isActive;

}
