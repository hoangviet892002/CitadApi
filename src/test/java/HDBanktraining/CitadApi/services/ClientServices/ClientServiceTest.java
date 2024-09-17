package HDBanktraining.CitadApi.services.ClientServices;

import HDBanktraining.CitadApi.dtos.response.ClientResponse;
import HDBanktraining.CitadApi.entities.CitadEntity;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.mappers.ClientMappers;
import HDBanktraining.CitadApi.repository.ClientRepo.ClientRepo;
import HDBanktraining.CitadApi.services.ClientServices.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

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
        // Mock data for citad entity
        CitadEntity citadEntity = new CitadEntity("citad_id", "Citad Name", "Branch");

        // Mock data for client entity
        ClientEntity existingClient = new ClientEntity("client_id", "Client Name", "client@example.com", "1234567890", "123 Street", "2000-01-01", 1000.0, citadEntity, new ArrayList<>(), new ArrayList<>());
        ClientEntity newClient = new ClientEntity("new_client_id", "New Client Name", "new_client@example.com", "0987654321", "456 Avenue", "1990-05-15", 500.0, citadEntity, new ArrayList<>(), new ArrayList<>());

        ClientResponse mockClientResponse = new ClientResponse("client_id", "Client Name", "client@example.com", "11092001", "1234567890", "123 Street", "2000-01-01", 1000.0);

//        // Config mock for client repo
//        when(clientRepo.findById("client_id")).thenReturn(existingClient);
//        when(clientRepo.findById("non_existent_id")).thenReturn(null);
//        when(clientRepo.findByNumber("1234567890")).thenReturn(existingClient);
//        when(clientRepo.findByNumber("non_existent_number")).thenReturn(null);
//
//        // Config mock for client mapper
//        when(ClientMappers.entityToResponse(existingClient)).thenReturn(mockClientResponse);
//        when(clientMappers.entityToResponse(newClient)).thenReturn(new ClientResponse("new_client_id", "New Client Name", "new_client@example.com", "11092001", "0987654321", "456 Avenue", "1990-05-15", 500.0));
//
//        doNothing().when(clientRepo).save(any(ClientEntity.class));
    }

    @Test
    public void testInsertClient_Success() {}

    @Test
    public void testInsertClient_AlreadyExists() {}

    @Test
    public void testCheckBalance_Success() {}

    @Test
    public void testCheckBalance_NotEnough() {}

    @Test
    public void testCheckBalance_ClientNotFound() {}

    @Test
    public void testGetClientById_Success() {}

    @Test
    public void testGetClientById_ClientNotFound() {}

    @Test
    public void testGetClientByNumber_Success() {}

    @Test
    public void testGetClientByNumber_Exception() {}

    @Test
    public void testUpdateBalance_Deposit() {}

    @Test
    public void testUpdateBalance_Withdraw() {}

}
