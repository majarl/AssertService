package eu.izzted.media_uploader.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        Path mPath = Paths.get(this.mediaStore);

        if (!this.validateConfig()) {
            log.error("Path {} does not exist. {}", mPath, mPath.toAbsolutePath());
            return;
        }

        String filename = file.getOriginalFilename();
        if (filename == null) {
            log.error("No filename");
            return;
        }

        Path destFile = mPath.resolve(filename);
        try {
            Files.copy(file.getInputStream(), destFile);
        } catch (IOException e) {
            log.error("Could not copy file: {}", e.getLocalizedMessage());
            return;
        }

        log.info("... Has saved {}", file.getOriginalFilename());
    }


    public boolean validateConfig() {
        if (mediaStore.trim().isEmpty()) {
            return false;
        }

        Path mediaStorePath = Paths.get(this.mediaStore);
        return Files.exists(mediaStorePath.toAbsolutePath());
    }


}
