package dev.kevinyu.service.restful.exception;

public class ServiceUnavailableException extends BaseResponseException {

    private static final int CODE = 503;

    public ServiceUnavailableException(String message) {
        super(CODE, message);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(CODE, cause);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

    public ServiceUnavailableException(String statusMessage, String reason) {
        super(CODE, statusMessage, reason);
    }

    public ServiceUnavailableException(String statusMessage, String reason, Throwable cause) {
        super(CODE, statusMessage, reason, cause);
    }

    public ServiceUnavailableException(String statusMessage, String reason, String domain) {
        super(CODE, statusMessage, reason, domain);
    }

    public ServiceUnavailableException(String statusMessage, String reason, String domain,
                                       Throwable cause) {
        super(CODE, statusMessage, reason, domain, cause);
    }
}
