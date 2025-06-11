package eu.izzted.media_uploader;

import eu.izzted.media_uploader.store.MediaStoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MediaStoreConfig.class)
public class MediaUploaderApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediaUploaderApplication.class, args);
	}
}
