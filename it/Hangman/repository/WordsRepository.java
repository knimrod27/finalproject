package fasttrack.it.Hangman.repository;

import fasttrack.it.Hangman.model.Category;
import fasttrack.it.Hangman.model.WordsEntry;
import fasttrack.it.Hangman.reader.WordsReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends JpaRepository<WordsEntry, Integer> {
}
