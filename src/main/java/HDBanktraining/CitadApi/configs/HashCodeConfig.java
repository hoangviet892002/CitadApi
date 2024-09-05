package HDBanktraining.CitadApi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import java.security.Key;

@Configuration
public class HashCodeConfig {

    @Bean
    public Key HashCodeKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate hash code key", e);
        }

    }
}
