package api.kokonut.moodzh.api.exceptions.http;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String message) {
        super(message);
    }
}
