package eu.izzted.media_uploader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media-uploader")
public class MediaUploadEndpoints {

    private static final Logger log = LoggerFactory.getLogger(MediaUploadEndpoints.class);

    @GetMapping("/")
    public String info() {
        return "MediaUploadEndpoints here";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        log.info("Got this file: {}", file.getOriginalFilename());
        return file.getName();
    }

}
