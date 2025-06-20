package eu.izzted.media_converter.endpoints;

import org.apache.tomcat.jni.Buffer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@RestController
@RequestMapping("/api/converter")
public class ConvertEndpoints {

    @GetMapping("")
    public String info() {
        return "Convert endpoints";
    }

    @GetMapping("/pwd")
    public String pwd() {
        String[] commands = { "pwd" };
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);

        try {
            Process p = processBuilder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitCode = p.waitFor();

            output.append("Exit code: ")
                    .append(exitCode);

            return output.toString();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @GetMapping("/convert_test")
    public String convertTest() {
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
