package fasttrack.it.Hangman.model;

import java.util.Arrays;
import java.util.Optional;

public enum Category {
    ANIMALS,
    FRUITS,
    COUNTRIES;

    public static Category of(String value) {
         return Arrays.stream(Category.values())
                .filter(category -> category.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Couldn't find a category for this value"));
    }
}
