package dev.kevinyu.service.restful.exception;

public class InternalServerErrorException extends BaseResponseException {

    private static final int CODE = 500;

    public InternalServerErrorException(String message) {
        super(CODE, message);
    }

    public InternalServerErrorException(Throwable cause) {
        super(CODE, cause);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

    public InternalServerErrorException(String statusMessage, String reason) {
        super(CODE, statusMessage, reason);
    }

    public InternalServerErrorException(String statusMessage, String reason, Throwable cause) {
        super(CODE, statusMessage, reason, cause);
    }

    public InternalServerErrorException(String statusMessage, String reason, String domain) {
        super(CODE, statusMessage, reason, domain);
    }

    public InternalServerErrorException(String statusMessage, String reason, String domain,
                                        Throwable cause) {
        super(CODE, statusMessage, reason, domain, cause);
    }
}
