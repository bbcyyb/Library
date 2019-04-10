package dev.kevinyu.service.restful.exception;

public class BadRequestException extends BaseResponseException {

    private static final int CODE = 400;

    public BadRequestException(String message) {
        super(CODE, message);
    }

    public BadRequestException(Throwable cause) {
        super(CODE, cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

    public BadRequestException(String statusMessage, String reason) {
        super(CODE, statusMessage, reason);
    }

    public BadRequestException(String statusMessage, String reason, Throwable cause) {
        super(CODE, statusMessage, reason, cause);
    }

    public BadRequestException(String statusMessage, String reason, String domain) {
        super(CODE, statusMessage, reason, domain);
    }

    public BadRequestException(String statusMessage, String reason, String domain, Throwable cause) {
        super(CODE, statusMessage, reason, domain, cause);
    }
}
