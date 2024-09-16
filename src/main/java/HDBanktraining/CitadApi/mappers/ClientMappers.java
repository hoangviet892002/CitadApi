package HDBanktraining.CitadApi.mappers;

import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.entities.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientMappers {
    // Converts ClientEntity to ClientResponse
    public static ClientResponse entityToResponse(ClientEntity clientEntity) {
        if (clientEntity == null) {
            return null;
        }
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(clientEntity.getId());
        clientResponse.setName(clientEntity.getName());
        clientResponse.setEmail(clientEntity.getEmail());
        clientResponse.setNumber(clientEntity.getNumber());
        clientResponse.setPhone(clientEntity.getPhone());
        clientResponse.setAddress(clientEntity.getAddress());
        clientResponse.setDob(clientEntity.getDob());
        clientResponse.setWallet(clientEntity.getWallet());

        return clientResponse;
    }

    // Converts ClientResponse to ClientEntity
    public static ClientEntity responseToEntity(ClientResponse clientResponse, CitadEntity citadEntity) {
        if (clientResponse == null) {
            return null;
        }
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(clientEntity.getId());
        clientEntity.setName(clientResponse.getName());
        clientEntity.setEmail(clientResponse.getEmail());
        clientEntity.setNumber(clientResponse.getNumber());
        clientEntity.setPhone(clientResponse.getPhone());
        clientEntity.setAddress(clientResponse.getAddress());
        clientEntity.setDob(clientResponse.getDob());
        clientEntity.setWallet(clientResponse.getWallet());
        clientEntity.setCitad(citadEntity); // Set CitadEntity

        return clientEntity;
    }
}
