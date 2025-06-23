package eu.izzted.media_converter.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ConverterService {

    // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ExecutorService.html

    private static final Logger log = LoggerFactory.getLogger(ConverterService.class);

    @Value("${media-store.location}")
    private String mediaStoreLocation;

    public void convertFile(String path) {
        log.info("Conversion has been requested for {} at {}", path, mediaStoreLocation);
        ConvertTask task = new ConvertTask(path);
        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            executorService.submit(task);
        }
    }


}
