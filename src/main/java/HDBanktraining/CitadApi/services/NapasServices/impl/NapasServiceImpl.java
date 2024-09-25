package HDBanktraining.CitadApi.services.NapasServices.impl;


import HDBanktraining.CitadApi.dtos.BaseList;
import HDBanktraining.CitadApi.dtos.napasDto.response.Bank;
import HDBanktraining.CitadApi.dtos.napasDto.response.Client;
import HDBanktraining.CitadApi.dtos.response.BaseReponse;
import HDBanktraining.CitadApi.services.NapasServices.NapasService;
import HDBanktraining.CitadApi.shared.enums.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class NapasServiceImpl  implements NapasService {

    private final String NAPAS_API = "http://localhost:8089/api/v1/";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<BaseReponse<BaseList<Bank>>> getBanks() {
        BaseList<Bank> banks = restTemplate.getForObject(NAPAS_API + "bank", BaseList.class);
        if (Objects.isNull(banks)) {
            return Mono.just(BaseReponse.<BaseList<Bank>>builder().responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode()).message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
        }
        return Mono.just(BaseReponse.<BaseList<Bank>>builder().responseCode(ResponseEnum.SUCCESS.getResponseCode()).message(ResponseEnum.SUCCESS.getMessage()).data(banks).build());
    }

    @Override
    public Mono<BaseReponse<Client>> getClient(String number, String bankCode) {
        Client client = restTemplate.getForObject(NAPAS_API + "client?number=" + number + "&bank=" + bankCode, Client.class);

        if (Objects.isNull(client)) {
            return Mono.just(BaseReponse.<Client>builder().responseCode(ResponseEnum.DATA_NOT_FOUND.getResponseCode()).message(ResponseEnum.DATA_NOT_FOUND.getMessage()).build());
        }
        return Mono.just(BaseReponse.<Client>builder().responseCode(ResponseEnum.SUCCESS.getResponseCode()).message(ResponseEnum.SUCCESS.getMessage()).data(client).build());
    }
}
