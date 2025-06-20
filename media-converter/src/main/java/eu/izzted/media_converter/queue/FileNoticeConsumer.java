package eu.izzted.media_converter.queue;

import eu.izzted.media_converter.convert.ConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileNoticeConsumer {

    private final static Logger log = LoggerFactory.getLogger(FileNoticeConsumer.class);

    private final ConverterService converter;

    @Autowired
    public FileNoticeConsumer(RabbitConfig config, ConverterService converter) {
        this.converter = converter;
    }


    @RabbitListener(queues = {"${file.queue.name}"}, errorHandler = "fileNoticeErrorHandler")
    public void consume(String msg) {
        log.info("Got message: {}", msg);
        this.converter.convertFile(msg);
    }


}
