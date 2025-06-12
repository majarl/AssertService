package eu.izzted.media_uploader.store;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ConfigurationProperties("media-store")
public class MediaStoreConfig {

    private String location = "uploaded_media_files";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static ConfigValidationResult validateConf(MediaStoreConfig conf) {
        if (conf.getLocation().isEmpty()) {
            return ConfigValidationResult.failed("Location is empty.");
        }

        Path basePath = Paths.get(conf.getLocation());
        if (Files.notExists(basePath)) {
            return ConfigValidationResult.failed("Storage volume at '"
                    + basePath
                    + "', ('"
                    + basePath.toAbsolutePath()
                    + "') "
                    + "does not exist. Consider creating it."
            );
        }

        return ConfigValidationResult.ok();
    }
}
