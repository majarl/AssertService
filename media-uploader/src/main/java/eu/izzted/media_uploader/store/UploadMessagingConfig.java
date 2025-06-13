package eu.izzted.media_uploader.store;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadMessagingConfig {

    private String queueName = "fileNotice";

    @Bean
    public UploadMessaging uploadMessaging(RabbitTemplate rabbitTemplate) {
        return new UploadMessaging(rabbitTemplate, queueName);
    }

}
