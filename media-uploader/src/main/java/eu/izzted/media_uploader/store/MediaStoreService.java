package eu.izzted.media_uploader.store;

import eu.izzted.media_uploader.ResultOf;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface MediaStoreService {
    ResultOf<Path> storeFile(MultipartFile file);
    boolean validateConfig();
}
