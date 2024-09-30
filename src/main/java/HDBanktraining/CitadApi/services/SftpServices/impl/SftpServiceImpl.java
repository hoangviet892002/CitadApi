package HDBanktraining.CitadApi.services.SftpServices.impl;

import HDBanktraining.CitadApi.services.CitadServices.impl.CitadServiceImpl;
import HDBanktraining.CitadApi.services.SftpServices.SftpService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Service
public class SftpServiceImpl implements SftpService {

    private static final Logger logger = Logger.getLogger(SftpServiceImpl.class);

    @Autowired
    private DefaultSftpSessionFactory sftpSessionFactory;

    @Override
    public Mono<Void> downloadFile(String remoteFilePath, String localFilePath) {
        return Mono.fromRunnable(() -> {
            logger.info("Starting file download from remote path: " + remoteFilePath + " to local path: " + localFilePath);
            try (var session = sftpSessionFactory.getSession()) {
                logger.info("SFTP session established successfully.");

                try (InputStream inputStream = session.readRaw(remoteFilePath);
                     FileOutputStream outputStream = new FileOutputStream(new File(localFilePath))) {

                    logger.info("Reading file from SFTP server: " + remoteFilePath);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    long totalBytesRead = 0;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                    }

                    logger.info("File downloaded successfully, total bytes read: " + totalBytesRead);
                }
            } catch (Exception e) {
                logger.error("Failed to download file from SFTP: " + remoteFilePath, e);
                throw new RuntimeException("File download failed", e);
            }
        });
    }

}
