package HDBanktraining.CitadApi.services.TransferNapasServices.impl;

import HDBanktraining.CitadApi.dtos.napasDto.request.NapasTransferRequest;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferNapasEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import HDBanktraining.CitadApi.repository.TransferNapas.TransferNapasRepo;
import HDBanktraining.CitadApi.services.TransferNapasServices.TransferNapasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class TransferNapasServiceImpl implements TransferNapasService {

    private final TransferNapasRepo transferNapasRepo;

    public TransferNapasServiceImpl(TransferNapasRepo transferNapasRepo) {
        this.transferNapasRepo = transferNapasRepo;
    }

    @Override
    public Mono<Void> insert(TransactionEntity transaction, String accountNumber, String bankCode) {
        TransferNapasEntity transferNapasEntity = new TransferNapasEntity(
                transaction, accountNumber, bankCode
        );
        TransferNapasEntity newTransfer = transferNapasRepo.save(transferNapasEntity);
        return Mono.empty();
    }

    @Override
    public Mono<TransferNapasEntity> getTransactionById(String id) {
        return Mono.justOrEmpty(transferNapasRepo.findByTransactionId(id));
    }


}
