package fasttrack.it.Hangman.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "words")
public class HangmanConfig {
    public String getFileLocation() {
        return fileLocation;
    }

    private String fileLocation;

        @PostConstruct
        void printConfig() {
            System.out.println("Country config = " + this);
        }

}
