package fasttrack.it.Hangman.reader;

import fasttrack.it.Hangman.config.HangmanConfig;
import fasttrack.it.Hangman.model.Category;
import fasttrack.it.Hangman.model.WordsEntry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@RequiredArgsConstructor

public class WordsReader {
    public static int ID =1;
    private final HangmanConfig config;



    public List<WordsEntry> readWords() {
        List<WordsEntry> words = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(config.getFileLocation()))) {
            return reader.lines()
                    .map(this::parseWords)
                    .toList();

        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }

    }

    private WordsEntry parseWords(String line) {
        String[] tokens = line.split("[|]");
        return WordsEntry.builder()
                .id(ID++)
                .word(tokens[0])
                .category(Category.of(tokens[1]))
                .build();
    }


}
