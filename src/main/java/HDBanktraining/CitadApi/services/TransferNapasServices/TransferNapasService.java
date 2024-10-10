package HDBanktraining.CitadApi.services.TransferNapasServices;

import HDBanktraining.CitadApi.services.TransferNapasServices.operations.TransferNapasDelete;
import HDBanktraining.CitadApi.services.TransferNapasServices.operations.TransferNapasInsert;
import HDBanktraining.CitadApi.services.TransferNapasServices.operations.TransferNapasQuery;
import HDBanktraining.CitadApi.services.TransferNapasServices.operations.TransferNapasUpdate;

public interface TransferNapasService extends
        TransferNapasDelete,
        TransferNapasUpdate,
        TransferNapasInsert,
        TransferNapasQuery {
}
