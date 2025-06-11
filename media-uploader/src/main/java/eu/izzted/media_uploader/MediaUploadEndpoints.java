package eu.izzted.media_uploader;

import eu.izzted.media_uploader.store.MediaStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media-uploader")
public class MediaUploadEndpoints {

    private static final Logger log = LoggerFactory.getLogger(MediaUploadEndpoints.class);

    private final MediaStoreService mediaStoreService;


    @Autowired
    public MediaUploadEndpoints(MediaStoreService mediaStoreService) {
        this.mediaStoreService = mediaStoreService;
    }


    @GetMapping("/")
    public String info() {
        return "MediaUploadEndpoints here";
    }


    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        log.info("Got this file: {}", file.getOriginalFilename());
        this.mediaStoreService.storeFile(file);
        return file.getName();
    }

}
