package eu.izzted.media_converter.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileNoticeConsumer {

    @Autowired
    public FileNoticeConsumer(RabbitConfig config) { }


    @RabbitListener(queues = {"${file.queue.name}"})
    public void consume(String msg) {
        System.out.println("------> " + msg);
    }


}
