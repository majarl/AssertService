package eu.izzted.media_uploader.store;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadMessagingConfig {

    @Value("${file.queue.name}")
    private String queueName;

    @Bean
    public UploadMessaging uploadMessaging(RabbitTemplate rabbitTemplate) {
        System.out.println(" -----> " + queueName);
        return new UploadMessaging(rabbitTemplate, queueName);
    }

}
