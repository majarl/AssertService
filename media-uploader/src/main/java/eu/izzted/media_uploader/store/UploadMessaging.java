package eu.izzted.media_uploader.store;

import eu.izzted.media_uploader.ResultOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class UploadMessaging {

    private static final Logger log = LoggerFactory.getLogger(UploadMessaging.class);

    private final RabbitTemplate template;

    private final Queue queue;

    public UploadMessaging(RabbitTemplate rabbitTemplate, String queueName) {
        this.template = rabbitTemplate;
        this.queue = new Queue(queueName);
        log.info("Has created UploadMessaging with: {} and queueName {}",
                this.template, this.queue);
    }

    public ResultOf<String> fileNotice(Path file) {
        String filename = String.valueOf(file.getFileName());
        log.info("Will send notice about {} to {}", filename, queue.getName());
        this.template.convertAndSend(queue.getName(), filename);
        return ResultOf.success("Has send: " + filename);
    }

}
