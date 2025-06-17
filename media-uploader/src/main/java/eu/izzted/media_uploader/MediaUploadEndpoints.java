package eu.izzted.media_uploader;

import eu.izzted.media_uploader.store.MediaStoreService;
import eu.izzted.media_uploader.store.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/media-uploader")
public class MediaUploadEndpoints {

    private static final Logger log = LoggerFactory.getLogger(MediaUploadEndpoints.class);

    private final MediaStoreService mediaStoreService;
    private final RabbitConfig rabbitConfig;


    @Autowired
    public MediaUploadEndpoints(
            MediaStoreService mediaStoreService,
            RabbitConfig rabbitConfig
    ) {
        this.mediaStoreService = mediaStoreService;
        this.rabbitConfig = rabbitConfig;
    }

    @GetMapping("/")
    public String info() {
        return "MediaUploadEndpoints here";
    }

    @PostMapping("/upload")
    public ResultOf<Path> fileUpload(@RequestParam("file") MultipartFile file) {
        log.info("Got this file: {}", file.getOriginalFilename());
        ResultOf<Path> result = this.mediaStoreService.storeFile(file);
        return result;
    }

    @GetMapping("/queueconf")
    public ResultOf<RabbitConfig> queueConfig() {
        return ResultOf.success(this.rabbitConfig);
    }

}
