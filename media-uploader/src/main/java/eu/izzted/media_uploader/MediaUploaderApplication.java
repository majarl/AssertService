package eu.izzted.media_uploader;

import eu.izzted.media_uploader.store.ConfigValidationResult;
import eu.izzted.media_uploader.store.MediaStoreConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MediaStoreConfig.class)
public class MediaUploaderApplication {

	private final MediaStoreConfig mediaStoreConfig;

	@Autowired
	public MediaUploaderApplication(MediaStoreConfig mediaStoreConfig) {
		this.mediaStoreConfig = mediaStoreConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(MediaUploaderApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.out.println("------------------------------");
		System.out.println(this.mediaStoreConfig.getLocation());
		System.out.println("------------------------------");

		ConfigValidationResult configValidationResult =
				MediaStoreConfig.validateConf(this.mediaStoreConfig);

		if (configValidationResult.hasFailed()) {
			System.out.println("Failed config: " + configValidationResult.msg);
			System.exit(-1);
		}
	}
}
