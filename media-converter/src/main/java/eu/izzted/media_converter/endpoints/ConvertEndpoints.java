package eu.izzted.media_converter.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/converter")
public class ConvertEndpoints {

    @GetMapping("")
    public String info() {
        return "Convert endpoints";
    }



}
