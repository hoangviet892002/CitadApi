package HDBanktraining.CitadApi.services.TranferTransactionService.impl;

import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import HDBanktraining.CitadApi.repository.TransferTransactionRepo.TransferTransactionRepo;
import HDBanktraining.CitadApi.services.TranferTransactionService.TranferTransactionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class TranferTransactionImpl  implements TranferTransactionService {
    private final TransferTransactionRepo transferTransactionRepo;

    public TranferTransactionImpl(TransferTransactionRepo transferTransactionRepo) {
        this.transferTransactionRepo = transferTransactionRepo;
    }

    @Override
    public Mono<Void> insert(TransactionEntity transaction, ClientEntity receiver) {
        TransferTransactionEntity transferTransactionEntity = new TransferTransactionEntity(
                receiver, transaction
        );
        TransferTransactionEntity  newTransfer =  transferTransactionRepo.save(transferTransactionEntity);
        return Mono.empty();
    }

    @Override
    public Mono<TransferTransactionEntity> getTransactionById(String id) {
        return Mono.just(transferTransactionRepo.findByTransactionId(id));
    }
}
