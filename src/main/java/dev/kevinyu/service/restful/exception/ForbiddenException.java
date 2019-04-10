package dev.kevinyu.service.restful.exception;

public class ForbiddenException extends BaseResponseException {

    private static final int CODE = 403;

    public ForbiddenException(String message) {
        super(CODE, message);
    }

    public ForbiddenException(Throwable cause) {
        super(CODE, cause);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

    public ForbiddenException(String statusMessage, String reason) {
        super(CODE, statusMessage, reason);
    }

    public ForbiddenException(String statusMessage, String reason, Throwable cause) {
        super(CODE, statusMessage, reason, cause);
    }

    public ForbiddenException(String statusMessage, String reason, String domain) {
        super(CODE, statusMessage, reason, domain);
    }

    public ForbiddenException(String statusMessage, String reason, String domain, Throwable cause) {
        super(CODE, statusMessage, reason, domain, cause);
    }
}
