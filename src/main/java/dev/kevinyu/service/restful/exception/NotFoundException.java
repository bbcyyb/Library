package dev.kevinyu.service.restful.exception;

public class NotFoundException extends BaseResponseException {

    private static final int CODE = 404;

    public NotFoundException(String message) {
        super(CODE, message);
    }

    public NotFoundException(Throwable cause) {
        super(CODE, cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

    public NotFoundException(String statusMessage, String reason) {
        super(CODE, statusMessage, reason);
    }

    public NotFoundException(String statusMessage, String reason, Throwable cause) {
        super(CODE, statusMessage, reason, cause);
    }

    public NotFoundException(String statusMessage, String reason, String domain) {
        super(CODE, statusMessage, reason, domain);
    }

    public NotFoundException(String statusMessage, String reason, String domain, Throwable cause) {
        super(CODE, statusMessage, reason, domain, cause);
    }
}
