package eu.izzted.media_converter.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    private static final Logger log = LoggerFactory.getLogger(Converter.class);

    @Value("${media-store.location}")
    private String mediaStoreLocation;

    @Async
    public void convertFile(String filename) {
        log.info("Conversion has been requested for {} at {}", filename, mediaStoreLocation);
    }


}
