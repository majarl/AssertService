package eu.izzted.media_uploader.store;

import eu.izzted.media_uploader.ResultOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileMediaStoreService implements MediaStoreService {

    private static final Logger log = LoggerFactory.getLogger(FileMediaStoreService.class);

    private final Path mediaStorePath;

    private final UploadMessaging messaging;

    @Autowired
    public FileMediaStoreService(MediaStoreConfig config, UploadMessaging messaging) {
        log.info("Creating a storage service with location: {}", config.getLocation());
        this.mediaStorePath = Paths.get(config.getLocation());
        this.messaging = messaging;
    }


    @Override
    public ResultOf<Path> storeFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            log.error("No filename");
            return ResultOf.fail("No filename");
        }

        Path destFile = this.mediaStorePath.resolve(filename);
        try {
            Files.copy(file.getInputStream(),
                    destFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResultOf.fail("Could not store file", e);
        }

        ResultOf<String> messagingResult = this.messaging.fileNotice(destFile);
        if (messagingResult.failed()) {
            return ResultOf.fail(messagingResult.msg(), messagingResult.e());
        }

        log.info("Has saved {}", file.getOriginalFilename());
        return ResultOf.success(destFile);
    }


    public boolean validateConfig() {
        if (this.mediaStorePath.toAbsolutePath().toString().trim().isEmpty()) {
            return false;
        }

        return Files.exists(this.mediaStorePath.toAbsolutePath());
    }


}
