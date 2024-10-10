package HDBanktraining.CitadApi.services.KafkaServices.impl;

import HDBanktraining.CitadApi.services.KafkaServices.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    public void setMessageConverter(JsonMessageConverter messageConverter) {
        kafkaTemplate.setMessageConverter(messageConverter);
    }
}
