package HDBanktraining.CitadApi.services.CitadServices.operations;

public interface DownloadSftpService {
    void downloadFile(String remoteFilePath, String localFilePath);
}
