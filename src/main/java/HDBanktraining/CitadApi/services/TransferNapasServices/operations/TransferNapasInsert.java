package HDBanktraining.CitadApi.services.TransferNapasServices.operations;

import HDBanktraining.CitadApi.dtos.napasDto.request.NapasTransferRequest;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import reactor.core.publisher.Mono;

public interface TransferNapasInsert {
    Mono<Void> insert(TransactionEntity transaction, String accountNumber, String bankCode);
}
