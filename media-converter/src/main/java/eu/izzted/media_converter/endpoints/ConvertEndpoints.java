package eu.izzted.media_converter.endpoints;

import eu.izzted.media_converter.convert.ConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@RestController
@RequestMapping("/api/converter")
public class ConvertEndpoints {

    private static final Logger log = LoggerFactory.getLogger(ConvertEndpoints.class);

    private final ConverterService converter;

    @Autowired
    public ConvertEndpoints(ConverterService converter) {
        this.converter = converter;
    }

    @GetMapping("")
    public String info() {
        return "Convert endpoints";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam(value = "filename") String filename) {
        log.info("filename = {}", filename);
        this.converter.convertFile(filename);

        return "todo";
    }

    @GetMapping("/convert_test")
    public String convertTest() {
        // https://ffmpeg-api.com/learn/ffmpeg/recipe/live-streaming
        // ffmpeg -i oregano.mov -codec:v h264 -codec:a aac -map 0 -f hls -hls_time 10 -hls_list_size 0 output.m3u8
        String cmd = "ffmpeg -i oregano.mov -codec:v h264 -codec:a aac -map 0 -f hls -hls_time 10 -hls_list_size 0 output.m3u8";

        String[] commands = cmd.split(" ");

        System.out.println("---> commands: \n" + Arrays.toString(commands));


        ProcessBuilder pb = new ProcessBuilder();
        pb.command(commands);

        StringBuilder sb = new StringBuilder();

        System.out.println("---> STARTING...");
        try {
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            int exitCode = p.waitFor();
            System.out.println("---> Ended with code: " + exitCode);

            sb.append("    Exit code: ")
                    .append(exitCode);
        } catch (IOException | InterruptedException e) {
            sb.append(e.getLocalizedMessage());
        }

        return sb.toString();
    }

}
