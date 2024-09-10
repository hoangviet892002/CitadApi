package HDBanktraining.CitadApi.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;


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
}
