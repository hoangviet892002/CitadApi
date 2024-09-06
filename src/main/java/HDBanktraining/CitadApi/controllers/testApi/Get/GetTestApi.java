package HDBanktraining.CitadApi.controllers.testApi.Get;


import HDBanktraining.CitadApi.utils.EmailUtil;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Test API")
@RequestMapping("/api/v1/test")
@SecurityRequirement(name = "Api-Key")
public class GetTestApi {

    @Autowired
    private EmailUtil emailUtil;

    private static final Logger logger = Logger.getLogger(GetTestApi.class);

    @GetMapping("/")
    @Operation(summary = "Test API", description = "Test API")
    public Mono<String> test() {
        logger.info("Infor");
        try {
            emailUtil.sendOtpMessage("chotrongnha@gmail.com", "123455");
        }catch (Exception e){
            logger.error("Error", e);
        }

        return Mono.just("Hello");
    }
}
