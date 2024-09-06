package HDBanktraining.CitadApi.services.SftpServices.operations;

public interface DownloadSftpService {
    void downloadFile(String remoteFilePath, String localFilePath);
}
