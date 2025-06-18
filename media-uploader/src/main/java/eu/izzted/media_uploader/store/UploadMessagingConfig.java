package eu.izzted.media_uploader.store;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadMessagingConfig {

    private final FileQueueProperties properties;

    @Autowired
    public UploadMessagingConfig(FileQueueProperties properties) {
        this.properties = properties;
    }

    @Bean
    public UploadMessaging uploadMessaging(RabbitTemplate rabbitTemplate) {
        return new UploadMessaging(rabbitTemplate, properties.getName());
    }

}
