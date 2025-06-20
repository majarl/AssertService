package eu.izzted.media_converter;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MediaConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaConverterApplication.class, args);
	}

}
