package eu.izzted.media_converter.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConverterService {

    private static final Logger log = LoggerFactory.getLogger(ConverterService.class);

    private static final ExecutorService exeService = Executors.newFixedThreadPool(5);

    @Value("${media-store.location}")
    private String mediaStoreLocation;

    public String convertFile(String path) {
        log.info("Conversion has been requested for {} at {}", path, mediaStoreLocation);
        ConvertTask task = new ConvertTask(path);
        exeService.submit(task);
        log.info("exeService: {}", exeService);
        return task.getId();
    }


}
