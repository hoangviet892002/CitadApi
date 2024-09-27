package HDBanktraining.CitadApi.services.SftpServices.operations;

import reactor.core.publisher.Mono;

public interface DownloadSftpService {
    Mono<Void> downloadFile(String remoteFilePath, String localFilePath);
}
