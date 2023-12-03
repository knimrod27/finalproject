package fasttrack.it.Hangman.exception;

public class ResourceNotFoundException extends RuntimeException{
    private final int resourceId;

    public ResourceNotFoundException(String message, int resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }
}
