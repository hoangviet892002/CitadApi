package HDBanktraining.CitadApi.services.ClientServices;

import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.mappers.ClientMappers;
import HDBanktraining.CitadApi.repository.ClientRepo.ClientRepo;
import HDBanktraining.CitadApi.services.ClientServices.impl.ClientServiceImpl;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "HOST=123",
        "USERNAME=test",
        "PASSWORD=testpassword"
})
public class ClientServiceTest {

    @Autowired
    private ClientServiceImpl clientService;

    @MockBean
    private ClientRepo clientRepo;

    @MockBean
    private ClientMappers clientMappers;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testInsertClient_Success() {
        // Arrange
        CitadEntity citadEntity = new CitadEntity("citad_id", "Citad Name", "Branch");
        ClientEntity newClient = new ClientEntity("new_client_id", "New Client", "new_client@example.com",
                "0987654321", "456 Avenue", "1990-05-15", 500.0, citadEntity, new ArrayList<>(), new ArrayList<>());

        when(clientRepo.findByEmail(newClient.getEmail())).thenReturn(null);

        // Act
        clientService.insertClient(citadEntity).subscribe();

        // Assert
        verify(clientRepo, times(1)).save(any(ClientEntity.class));
    }

    @Test
    public void testInsertClient_AlreadyExists() {
        // Arrange
        CitadEntity citadEntity = new CitadEntity("citad_id", "Citad Name", "Branch");
        ClientEntity clientEntity = ClientEntity.DefaultEntites(citadEntity);

        when(clientRepo.findByEmail(clientEntity.getEmail())).thenReturn(clientEntity);

        // Act
        clientService.insertClient(citadEntity).subscribe();

        // Assert
        verify(clientRepo, never()).save(any(ClientEntity.class));
        verify(clientRepo, times(1)).findByEmail(clientEntity.getEmail());
    }

    @Test
    public void testCheckBalance_Success() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setWallet(1000.0);

        when(clientRepo.findById("client_id")).thenReturn(client);

        // Act
        Mono<Boolean> result = clientService.checkBalance("client_id", 500.0);

        // Assert
        assertTrue(result.block());
    }

    @Test
    public void testCheckBalance_NotEnough() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setWallet(300.0);

        when(clientRepo.findById("client_id")).thenReturn(client);

        // Act
        Mono<Boolean> result = clientService.checkBalance("client_id", 500.0);

        // Assert
        assertFalse(result.block());
    }

    @Test
    public void testCheckBalance_ClientNotFound() {
        // Arrange
        when(clientRepo.findById("client_id")).thenReturn(null);

        // Act
        Mono<Boolean> result = clientService.checkBalance("client_id", 500.0);

        // Assert
        assertFalse(result.block());
    }

    @Test
    public void testGetClientById_Success() {
        // Arrange
        ClientEntity client = new ClientEntity();
        when(clientRepo.findById("client_id")).thenReturn(client);

        // Act
        Mono<ClientEntity> result = clientService.getClientById("client_id");

        // Assert
        assertNotNull(result.block());
    }

    @Test
    public void testGetClientById_ClientNotFound() {
        // Arrange
        when(clientRepo.findById("client_id")).thenReturn(null);

        // Act
        Mono<ClientEntity> result = clientService.getClientById("client_id");

        // Assert
        assertTrue(result.blockOptional().isEmpty());
    }

    @Test
    public void testGetClientByNumber_Success() {
        // Arrange
        ClientEntity client = new ClientEntity();
        ClientResponse clientResponse = new ClientResponse();
        when(clientRepo.findByNumber("1234567890")).thenReturn(client);
        when(clientMappers.entityToResponse(client)).thenReturn(clientResponse);

        // Act
        Mono<BaseReponse<ClientResponse>> result = clientService.getClientByNumber("1234567890");

        // Assert
        assertNotNull(result.block());
        assertEquals(ResponseEnum.SUCCESS.getResponseCode(), result.block().getResponseCode());
    }

    @Test
    public void testGetClientByNumber_Exception() {
        // Act
        Mono<BaseReponse<ClientResponse>> result = clientService.getClientByNumber("invalid");

        // Assert
        assertNotNull(result.block());
        assertEquals(ResponseEnum.BAD_REQUEST.getResponseCode(), result.block().getResponseCode());
    }

    @Test
    public void testUpdateBalance_Deposit() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setWallet(1000.0);

        // Act
        clientService.updateBalance(client, 500.0, true).subscribe();

        // Assert
        assertEquals(1500.0, client.getWallet());
        verify(clientRepo, times(1)).save(client);
    }

    @Test
    public void testUpdateBalance_Withdraw() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setWallet(1000.0);

        // Act
        clientService.updateBalance(client, 500.0, false).subscribe();

        // Assert
        assertEquals(500.0, client.getWallet());
        verify(clientRepo, times(1)).save(client);
    }

}
