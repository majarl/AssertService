package eu.izzted.media_uploader.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileMediaStoreService implements MediaStoreService {

    private static final Logger log = LoggerFactory.getLogger(FileMediaStoreService.class);

    private final String mediaStore;

    @Autowired
    public FileMediaStoreService(MediaStoreConfig config) {
        log.info("Creating a storage service with location: {}", config.getLocation());
        this.mediaStore = config.getLocation();
    }

    @Override
    public void storeFile(MultipartFile file) {
        log.info("Will save file {} to store... at {}",
                file.getOriginalFilename(),
                mediaStore);

        log.info("... Has saved {}", file.getOriginalFilename());
    }

}
