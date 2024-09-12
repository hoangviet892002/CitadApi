package HDBanktraining.CitadApi.mappers;

import HDBanktraining.CitadApi.dtos.request.DataTransferRequest;
import HDBanktraining.CitadApi.dtos.response.TransferResponse;
import HDBanktraining.CitadApi.entities.ClientEntity;
import HDBanktraining.CitadApi.entities.TransactionEntity;

public class TransactionMappers {
    public TransactionEntity toTransactionEntity(DataTransferRequest request, ClientEntity sender, ClientEntity receiver) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
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
}
