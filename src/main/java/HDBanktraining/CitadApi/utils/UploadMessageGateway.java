package HDBanktraining.CitadApi.utils;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.io.File;


@MessagingGateway
public interface UploadMessageGateway {
    @Gateway(requestChannel = "toSftpChannel")
    void upload(File file);
}
