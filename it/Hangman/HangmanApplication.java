package fasttrack.it.Hangman;

import fasttrack.it.Hangman.service.HangmanGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class HangmanApplication implements ApplicationRunner {

	@Autowired
	private HangmanGameService service;

	public static void main(String[] args) {
		SpringApplication.run(HangmanApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}
}
