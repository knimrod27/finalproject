package fasttrack.it.Hangman.model;

import java.util.Arrays;
import java.util.Optional;

public enum Category {
    ANIMALS("animals"),
    FRUITS("fruits"),
    COUNTRIES("countries");

    private final String code;

    Category(String code) {
        this.code = code;
    }

    public static Category of(String value) {
         return Arrays.stream(Category.values())
                .filter(category -> category.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Couldn't find a category for this value"));
    }
}
