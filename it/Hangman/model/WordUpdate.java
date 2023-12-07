package fasttrack.it.Hangman.model;

import lombok.Builder;

@Builder
public record WordUpdate(
        String word,
        String category
) {
}
