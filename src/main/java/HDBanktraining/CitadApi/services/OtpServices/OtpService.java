package HDBanktraining.CitadApi.services.OtpServices;

import HDBanktraining.CitadApi.services.OtpServices.operations.DeleteOtpService;
import HDBanktraining.CitadApi.services.OtpServices.operations.InsertOtpService;
import HDBanktraining.CitadApi.services.OtpServices.operations.QueryOtpService;
import HDBanktraining.CitadApi.services.OtpServices.operations.UpdateOtpService;

public interface OtpService extends
        InsertOtpService, QueryOtpService, UpdateOtpService, DeleteOtpService
{
}
