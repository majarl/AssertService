package eu.izzted.media_uploader.store;

import org.springframework.web.multipart.MultipartFile;

public interface MediaStoreService {
    void storeFile(MultipartFile file);
}
