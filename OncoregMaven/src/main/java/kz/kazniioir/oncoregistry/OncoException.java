package kz.kazniioir.oncoregistry;

public class OncoException extends Exception {

    public OncoException() {
    }

    public OncoException(String message) {
        super(message);
    }

    public OncoException(String message, Throwable cause) {
        super(message, cause);
    }
}
