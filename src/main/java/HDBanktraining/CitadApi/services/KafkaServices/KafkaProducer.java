package HDBanktraining.CitadApi.services.KafkaServices;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.stereotype.Component;


public interface KafkaProducer {
    void sendMessage(String topic, Object message);

    // set message converter
    void setMessageConverter(JsonMessageConverter messageConverter);
}
