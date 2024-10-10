package HDBanktraining.CitadApi.services.NapasServices;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.request.NapasTransferRequest;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.napasDto.response.NapasTransferResponse;
import HDBanktraining.CitadApi.dtos.request.AcceptTransferRequest;
import HDBanktraining.CitadApi.dtos.response.AcceptTransferResponse;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface NapasService {

     Mono<BaseReponse<BaseList<Bank>>> getBanks();

     Mono<BaseReponse<Client>> getClient(String number, String bankCode);

     Mono<BaseReponse<NapasTransferResponse>> transfer(NapasTransferRequest napasTransferRequest);

      Mono<BaseReponse<AcceptTransferResponse>> acceptTransfer(AcceptTransferRequest acceptTransferRequest);


}
