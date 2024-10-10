package HDBanktraining.CitadApi.configs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import java.io.File;


@Configuration
public class SftpConfig {
    @Value("${spring.application.sftp.host}")
    private String host = "";
    @Value("${spring.application.sftp.port}")
    private int port = 22;
    @Value("${spring.application.sftp.user}")
    private String user = "";
    @Value("${spring.application.sftp.password}")
    private String password = "";

    private static Logger logger = Logger.getLogger(SftpConfig.class.getName());

    @Bean
    public DefaultSftpSessionFactory sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(host);
        factory.setPort(port);
        factory.setUser(user);
        factory.setPassword(password);
        factory.setAllowUnknownKeys(true);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "toSftpChannel")
    public SftpMessageHandler handler() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression("C:\\Users\\Administrator\\Documents\\Transaction"));
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof File) {
                return ((File) message.getPayload()).getName();
            } else {
                throw new IllegalArgumentException("File expected as payload.");
            }
        });
        logger.info("File uploaded");
        return handler;
    }
}
