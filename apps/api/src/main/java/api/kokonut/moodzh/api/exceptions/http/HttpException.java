package api.kokonut.moodzh.api.exceptions.http;

import org.springframework.http.HttpStatusCode;

public class HttpException extends RuntimeException {
    public HttpException(String error, HttpStatusCode status) {

        super("status=" + status + ":error=" + error);
    }
}
