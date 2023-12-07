package fasttrack.it.Hangman.service;

import fasttrack.it.Hangman.config.HangmanConfig;
import fasttrack.it.Hangman.exception.ResourceNotFoundException;
import fasttrack.it.Hangman.model.Category;
import fasttrack.it.Hangman.model.WordUpdate;
import fasttrack.it.Hangman.model.WordsEntry;
import fasttrack.it.Hangman.reader.WordsReader;
import fasttrack.it.Hangman.repository.WordsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HangmanGameService {
    private final WordsRepository repository;
    private final WordsReader reader;
    private final HangmanConfig config;
    public static int MAX_ATTEMPTS;

    public static String INPUT_FILE;

    private String wordToGuess;
    private Set<Character> guessedLetters;
    private int incorrectAttempts;


    @PostConstruct
   void init()  {
        MAX_ATTEMPTS = 6;
        INPUT_FILE = config.getFileLocation();
        repository.saveAll(reader.readWords());
    }


    public void startGame(String category) {
        wordToGuess = selectRandomWord(category, INPUT_FILE);
        guessedLetters = new HashSet<>();
        incorrectAttempts = 0;
    }

    public String displayWord() {
        StringBuilder display = new StringBuilder();
        for (char letter : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(letter)) {
                display.append(letter).append(" ");
            } else {
                display.append("_ ");
            }
        }
        return display.toString().trim();
    }

    public String makeGuess(char guess) {
        if (guessedLetters.contains(guess)) {
            return "You already guessed that letter. Try again.";
        }

        guessedLetters.add(guess);

        if (wordToGuess.indexOf(guess) == -1) {
            incorrectAttempts++;
            return "Incorrect guess. Attempts left: " + (MAX_ATTEMPTS - incorrectAttempts);
        }

        return "Correct guess!";
    }

    public boolean isGameOver() {
        return incorrectAttempts >= MAX_ATTEMPTS || isGameWon();
    }

    public boolean isGameWon() {
        for (char letter : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(letter)) {
                return false;
            }
        }
        return true;
    }

    private String selectRandomWord(String category, String filePath) {
        try {
            List<String> wordsInCategory = Files.lines(Path.of(filePath))
                    .map(line -> line.split("\\|"))
                    .filter(parts -> parts.length == 2 && parts[1].trim().equalsIgnoreCase(category))
                    .map(parts -> parts[0].trim().toLowerCase())
                    .collect(Collectors.toList());

            if (wordsInCategory.isEmpty()) {
                throw new RuntimeException("No words found in the specified category.");
            }

            return wordsInCategory.get(new Random().nextInt(wordsInCategory.size()));
        } catch (IOException e) {
            throw new RuntimeException("Error reading words file.", e);
        }
    }


    public int getIncorrectAttempts() {
        return incorrectAttempts;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public List<WordsEntry> listAllWords() {
        return repository.findAll();
    }

    public Optional<WordsEntry> findById(int id) {
        return repository.findById(id);
    }

    public WordsEntry add(WordsEntry newWord) {
        return repository.save(newWord);
    }

    public WordsEntry update(int id, WordUpdate wordUpdate) {
        WordsEntry words = getOrThrow(id);
        WordsEntry updatedWord = words
                .withWord(wordUpdate.word() == null ? words.getWord() : wordUpdate.word())
                .withCategory(wordUpdate.category() == null ? words.getCategory() : Category.of(wordUpdate.category()));

        return repository.save(updatedWord);
    }

    public Optional<WordsEntry> delete(int id) {
        Optional<WordsEntry> wordOptional = findById(id);
        wordOptional.ifPresent(repository::delete);
        return wordOptional;
    }

    public WordsEntry replace(int id, WordsEntry newWord) {
        WordsEntry wordToReplace = getOrThrow(id);
        return repository.save(newWord.withId(wordToReplace.getId()));
    }



    private WordsEntry getOrThrow(int wordId) {
        return findById(wordId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find word with id %s".formatted(wordId), wordId));
    }



}
