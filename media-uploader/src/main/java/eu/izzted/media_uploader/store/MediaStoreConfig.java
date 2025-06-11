package eu.izzted.media_uploader.store;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("media-store")
public class MediaStoreConfig {

    private String location = "uploaded_media_files";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
