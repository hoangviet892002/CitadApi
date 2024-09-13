package HDBanktraining.CitadApi.services.ClientServices;

import HDBanktraining.CitadApi.services.ClientServices.operations.InsertClientService;
import HDBanktraining.CitadApi.services.ClientServices.operations.QueryClientService;
import HDBanktraining.CitadApi.services.ClientServices.operations.UpdateClientService;

public interface ClientService extends QueryClientService, InsertClientService , UpdateClientService {
}
