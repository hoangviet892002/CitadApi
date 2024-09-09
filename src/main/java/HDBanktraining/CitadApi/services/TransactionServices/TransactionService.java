package HDBanktraining.CitadApi.services.TransactionServices;


import HDBanktraining.CitadApi.services.TransactionServices.operations.InsertTransactionService;
import HDBanktraining.CitadApi.services.TransactionServices.operations.QueryTransactionService;

public interface TransactionService extends InsertTransactionService, QueryTransactionService {
}
