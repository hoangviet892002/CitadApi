package HDBanktraining.CitadApi.utils.converters;

import HDBanktraining.CitadApi.entities.TransactionEntity;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TransactionMessageConverter extends JsonMessageConverter {

    public TransactionMessageConverter() {
        super();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(DefaultJackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("HDBanktraining.CitadApi");
        typeMapper.setIdClassMapping(Collections.singletonMap("transaction", TransactionEntity.class));
        this.setTypeMapper(typeMapper);

    }
}
