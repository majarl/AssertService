package eu.izzted.media_converter.queue;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

public class FileNoticeErrorHandler implements RabbitListenerErrorHandler {

    private final static Logger log =
            LoggerFactory.getLogger(FileNoticeErrorHandler.class);

    @Override
    public Object handleError(Message amqpMessage,
                              Channel channel,
                              org.springframework.messaging.Message<?> message,
                              ListenerExecutionFailedException e) throws Exception {
        log.info("Could not connect. {}", e.getLocalizedMessage());
        return null;
    }
}
