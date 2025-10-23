package api.kokonut.moodzh.api.exceptions.collection;

public class CollectionException extends RuntimeException {
    public CollectionException(String error, Throwable cause) {
        super(error, cause);
    }
}
