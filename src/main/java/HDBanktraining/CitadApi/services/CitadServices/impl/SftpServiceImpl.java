package HDBanktraining.CitadApi.services.CitadServices.impl;

import HDBanktraining.CitadApi.services.CitadServices.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Service
public class SftpServiceImpl implements SftpService {

    @Autowired
    private DefaultSftpSessionFactory sftpSessionFactory;

    @Override
    public void downloadFile(String remoteFilePath, String localFilePath) {
        try (var session = sftpSessionFactory.getSession()) {
            try (InputStream inputStream = session.readRaw(remoteFilePath);
                 FileOutputStream outputStream = new FileOutputStream(new File(localFilePath))) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
