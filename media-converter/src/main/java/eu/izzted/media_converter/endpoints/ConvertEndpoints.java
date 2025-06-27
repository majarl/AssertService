package eu.izzted.media_converter.endpoints;

import eu.izzted.media_converter.convert.ConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/converter")
public class ConvertEndpoints {

    private static final Logger log = LoggerFactory.getLogger(ConvertEndpoints.class);

    private final ConverterService converter;

    ConvertStatusMessages msgStore = ConvertStatusMessages.instance();

    @Autowired
    public ConvertEndpoints(ConverterService converter) {
        this.converter = converter;
    }

    @GetMapping("")
    public String info() {
        return "Convert endpoints";
    }

    @PostMapping("/convert")
    public ConvertEndpointResponse convert(@RequestParam(value = "filename") String filename) {
        log.info("Attempting to convert: filename = {}", filename);
        var jobId = this.converter.convertFile(filename);
        return ConvertEndpointResponse.create(jobId, "Job started");
    }

    @GetMapping("/status")
    public List<ConvertMsg> status() {
        return msgStore.peekMessages();
    }

    @GetMapping("status/{jobId}")
    public ConvertStatusResponse convertStatusForJob(@PathVariable String jobId) {
        List<ConvertMsg> msgList = msgStore.getMessages(jobId);
        return new ConvertStatusResponse(jobId, msgList);
    }

}
