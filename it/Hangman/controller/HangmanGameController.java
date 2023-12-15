package fasttrack.it.Hangman.controller;

import fasttrack.it.Hangman.exception.ResourceNotFoundException;

import fasttrack.it.Hangman.model.WordUpdate;
import fasttrack.it.Hangman.model.WordsEntry;
import fasttrack.it.Hangman.service.HangmanGameService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("hangman")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class HangmanGameController {

    @Autowired
    private final HangmanGameService hangmanService;

    @GetMapping("play/{category}")
    public String chooseCategory(@PathVariable String category) {
        hangmanService.startGame(category);
        return "Game started. Guess the word: " + hangmanService.displayWord();
    }


    @GetMapping("/guess/{letter}")
    public String guessLetter(@PathVariable char letter) {

        if (hangmanService.isGameOver()) {
            return "Game over. Start a new game.";
        }

        String result = hangmanService.makeGuess(letter);
        result += " Your guess: " + hangmanService.displayWord();
        if (hangmanService.isGameOver()) {
            result += "\n" + getGameResult();
        }
        return result;
    }

    private String getGameResult() {
        if (hangmanService.isGameWon()) {
            return "Congratulations! You guessed the word: " + hangmanService.getWordToGuess();
        } else {
            return "The word was: " + hangmanService.getWordToGuess() +  ".\n Click on Restart Game to play again.";
        }
    }

    @GetMapping
    List<WordsEntry> getAll() {
        return hangmanService.listAllWords();
    }

    @GetMapping("{id}")
    WordsEntry getById(@PathVariable int id) {
        return hangmanService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find word with id %s".formatted(id), id));
    }

    @PostMapping
    WordsEntry addWord(@RequestBody WordsEntry word) {
        return hangmanService.add(word);
    }

    @PatchMapping("{id}")
    WordsEntry updateWord(@PathVariable int id, @RequestBody WordUpdate word) {
        return hangmanService.update(id, word);
    }

    @PutMapping("{id}")
    WordsEntry replaceWord(@PathVariable int id, @RequestBody WordsEntry word) {
        return hangmanService.replace(id, word);
    }

    @DeleteMapping("{id}")
    WordsEntry deleteWord(@PathVariable int id) {
        return hangmanService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find word with id %s".formatted(id), id));
    }
}


