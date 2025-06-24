package eu.izzted.media_converter.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class ConvertTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ConvertTask.class);

    private final String origFile;

    private final String id;

    private long t = 0;


    public ConvertTask(String path) {
        this.origFile = path;
        this.id = UUID.randomUUID().toString();
    }


    @Override
    public void run() {
        log.info("   >>>>> {} : Starting to convert {} ...", this.id, this.origFile);
        long dt = realConvert(this.origFile);
        log.info("   >>>>> {} : Conversion has ended for {}", this.id, this.origFile);
        log.info("Took about {}ms", dt);
    }


    private long fakeConvert() {
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


    private long realConvert(String path) {
        // [ffmpeg, -i, oregano.mov, -codec:v, h264, -codec:a, aac, -map, 0, -f, hls, -hls_time, 10, -hls_list_size, 0, output.m3u8]
        String[] command = {
                "ffmpeg",
                "-i", path,
                "-codec:v", "h264",
                "-codec:a", "aac",
                "-map", "0",
                "-f", "hls",
                "-hls_time", "10",
                "-hls_list_size", "0",
                path + ".m3u8"
        };

        log.info("About to execute: {}", Arrays.toString(command));

        StringBuilder outputBuffer = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(command);

        long begin = System.currentTimeMillis();

        try {
            Process p = pb.start();
            BufferedReader reader = createInputStream(p);

            String line;
            while ((line = reader.readLine()) != null) {
                outputBuffer.append(line).append("\n");
            }

            int exitCode = p.waitFor();

            log.info("Process done. exitCode = {}", exitCode);
            log.info("Output: {}", outputBuffer.toString());
            reader.close();
        } catch (IOException | InterruptedException e) {
            log.error("Exception: {}", e.getLocalizedMessage());
            return -1;
        }

        long end = System.currentTimeMillis();
        return end - begin;
    }


    private BufferedReader createInputStream(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }


    public String getId() {
        return id;
    }
}
