package eu.izzted.media_converter.queue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${file.queue.name}")
    private String queueName;

    @Bean
    public FileNoticeErrorHandler fileNoticeErrorHandler() {
        return new FileNoticeErrorHandler();
    }

}
