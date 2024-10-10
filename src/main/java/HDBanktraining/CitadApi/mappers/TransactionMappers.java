package HDBanktraining.CitadApi.mappers;

import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.TransactionResponse;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;
import HDBanktraining.CitadApi.entities.TransferNapasEntity;
import HDBanktraining.CitadApi.entities.TransferTransactionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TransactionMappers {
    public TransactionEntity toTransactionEntity(DataTransferRequest request, ClientEntity sender, ClientEntity receiver) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setSender(sender);
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getMessage());
        transaction.setType("TRANSFER");
        transaction.setStatus("PENDING");
        return transaction;
    }

    public TransferResponse toTransferResponse(TransactionEntity transaction, String otp) {
        TransferResponse response = new TransferResponse();
        response.setTransactionId(transaction.getId());
        response.setMessage("Transaction processed successfully.");
        response.setOtp(otp);
        return response;
    }
    public TransactionResponse toTransactionResponse(TransactionEntity transaction, TransferTransactionEntity transferTransaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(String.valueOf(transaction.getAmount()));
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt().toString());
        response.setUpdatedAt(transaction.getUpdatedAt().toString());
        response.setFrom(transaction.getSender().getId());
        response.setTo(transferTransaction.getReceiver().getId());
        response.setType(transaction.getType());
        return response;
    }
    public TransactionResponse toTransactionResponse(TransactionEntity transaction, TransferNapasEntity transferTransaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(String.valueOf(transaction.getAmount()));
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt().toString());
        response.setUpdatedAt(transaction.getUpdatedAt().toString());
        response.setFrom(transaction.getSender().getId());
        response.setTo(transferTransaction.getReceiverBank());
        response.setType(transaction.getType());
        return response;
    }


    public List<TransactionResponse> toTransactionResponse(List<TransactionEntity> content, List<TransferTransactionEntity> transferTransactionEntities) {
        return content.stream().map(transaction -> {
            TransferTransactionEntity transferTransaction = transferTransactionEntities.stream().filter(t -> t.getTransaction().getId().equals(transaction.getId())).findFirst().orElse(null);
            return transferTransaction != null ? toTransactionResponse(transaction, transferTransaction) : null;
        }).toList();
    }
}
