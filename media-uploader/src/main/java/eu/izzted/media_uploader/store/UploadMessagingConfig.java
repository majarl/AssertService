package eu.izzted.media_uploader.store;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadMessagingConfig {

    private final UploadMessageProperties properties;

    @Autowired
    public UploadMessagingConfig(UploadMessageProperties properties) {
        this.properties = properties;
    }

    @Bean
    public UploadMessaging uploadMessaging(RabbitTemplate rabbitTemplate) {
        System.out.println(" -----> " + properties.getName());
        return new UploadMessaging(rabbitTemplate, properties.getName());
    }

}
