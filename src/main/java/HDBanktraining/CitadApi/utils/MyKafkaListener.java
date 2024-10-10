package HDBanktraining.CitadApi.utils;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "myId", topics = "transaction")
public class MyKafkaListener {
    Logger logger = Logger.getLogger(MyKafkaListener.class);
    private final UploadMessageGateway uploadGateway;
    @KafkaHandler
    public void listen(Object message) {
        logger.info("Received message: " + message);
        // set file name by timestamp
        File file = new File("src/main/resources/transaction/transaction" + System.currentTimeMillis() + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
               logger.error("Error creating file", e);
           }
        }
        // write message to file
        FileUtil.writeFile(file, message);
        // upload file to SFTP server
        uploadGateway.upload(file);
        logger.info("Uploaded file: " + file.getName());

    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Unknown: " + object);
    }

}
