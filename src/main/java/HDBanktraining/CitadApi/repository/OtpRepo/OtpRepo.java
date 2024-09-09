package HDBanktraining.CitadApi.repository.OtpRepo;

import HDBanktraining.CitadApi.entities.OtpEntity;
import HDBanktraining.CitadApi.repository.OtpRepo.operations.OtpCreateRepository;
import HDBanktraining.CitadApi.repository.OtpRepo.operations.OtpDeleteRepository;
import HDBanktraining.CitadApi.repository.OtpRepo.operations.OtpReadRepository;
import HDBanktraining.CitadApi.repository.OtpRepo.operations.OtpUpdateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends
        JpaRepository<OtpEntity, Long>,
        OtpCreateRepository,
        OtpDeleteRepository,
        OtpReadRepository,
        OtpUpdateRepository
{
}
