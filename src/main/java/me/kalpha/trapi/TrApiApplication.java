package me.kalpha.trapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;

@SpringBootApplication
public class TrApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrApiApplication.class, args);
	}

	@Bean
	public ByteArrayJsonMessageConverter messageConverter() {
		return new ByteArrayJsonMessageConverter();
	}
}
