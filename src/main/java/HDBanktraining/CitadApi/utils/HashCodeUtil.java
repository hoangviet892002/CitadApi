package HDBanktraining.CitadApi.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class HashCodeUtil {
    private final Key key;

    private static final String HashCodeKey = "HashCodeKey";

    public HashCodeUtil(Key key) {
        this.key = key;
    }

    public String calculateHashCode(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getEncoded(), "HmacSHA256"));
            byte[] bytes = mac.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hash code", e);
        }
    }

    public boolean verifyHashCode(String data) {
        return calculateHashCode(data).equals(calculateHashCode(HashCodeKey));
    }
}
