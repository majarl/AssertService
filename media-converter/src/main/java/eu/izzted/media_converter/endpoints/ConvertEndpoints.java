package eu.izzted.media_converter.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/converter")
public class ConvertEndpoints {

    @GetMapping("")
    public String info() {
        return "Convert endpoints";
    }

    @GetMapping("/pwd")
    public String pwd() {
        String[] commands = { "ffmpeg", "-version" };
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

}
