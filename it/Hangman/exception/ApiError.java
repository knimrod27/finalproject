package fasttrack.it.Hangman.exception;

import lombok.Builder;

@Builder
public record ApiError(
        String message,
        int entityId,
        String internalErrorCode
) {
}
