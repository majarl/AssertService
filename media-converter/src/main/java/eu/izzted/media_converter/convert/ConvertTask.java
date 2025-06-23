package eu.izzted.media_converter.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

public class ConvertTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ConvertTask.class);

    private final String origFile;

    private long t = 0;


    public ConvertTask(String path) {
        this.origFile = path;
    }


    @Override
    public void run() {
        String taskUuid = UUID.randomUUID().toString();
        log.info(">>>>> {} : Starting to convert {} ...", taskUuid, this.origFile);
        this.t = this.convert();
        log.info(">>>>> {} : Conversion has ended for {}", taskUuid, this.origFile);
    }


    private long convert() {
        Random r = new Random();
        long min = 5_000;
        long max = 10_000;
        long z = min + (long) (r.nextDouble() * (max - min));

        try {
            log.info("... Convert convert convert ... {}", z);
            Thread.sleep(z);
        } catch (InterruptedException e) {
            log.warn("Interrupted: {}", e.getLocalizedMessage());
        }

        return z;
    }
}
