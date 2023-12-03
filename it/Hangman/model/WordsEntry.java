package fasttrack.it.Hangman.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.EnumType.*;


@Data
@Entity
@Builder
@With
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class WordsEntry {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String word;


@Enumerated(value = STRING)
    private Category category;

}
