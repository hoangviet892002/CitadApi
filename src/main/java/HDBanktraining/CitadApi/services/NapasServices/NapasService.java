package HDBanktraining.CitadApi.services.NapasServices;

import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import reactor.core.publisher.Mono;

public interface NapasService {

     Mono<BaseReponse<BaseList<Bank>>> getBanks();

     Mono<BaseReponse<Client>> getClient(String number);
}
