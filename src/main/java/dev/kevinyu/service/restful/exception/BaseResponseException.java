package dev.kevinyu.service.restful.exception;

public class BaseResponseException extends Exception{

    protected final int statusCode;
    protected final String reason;
    protected final String domain;

    public int getStatusCode() {
        return statusCode;
    }

    public String getReason() {
        return reason;
    }

    public String getDomain() {
        return domain;
    }

    protected BaseResponseException(int statusCode, String statusMessage) {
        super(statusMessage);

        this.statusCode = statusCode;
        this.reason = null;
        this.domain = null;
    }

    protected BaseResponseException(int statusCode, Throwable cause) {
        super(cause);

        this.statusCode = statusCode;
        this.reason = null;
        this.domain = null;
    }

    protected BaseResponseException(int statusCode, String statusMessage, Throwable cause) {
        super(statusMessage, cause);

        this.statusCode = statusCode;
        this.reason = null;
        this.domain = null;
    }

    protected BaseResponseException(int statusCode, String statusMessage, String reason) {
        super(statusMessage);

        this.statusCode = statusCode;
        this.reason = reason;
        this.domain = null;
    }

    protected BaseResponseException(int statusCode, String statusMessage, String reason, Throwable cause) {
        super(statusMessage, cause);

        this.statusCode = statusCode;
        this.reason = reason;
        this.domain = null;
    }

    protected BaseResponseException(int statusCode, String statusMessage, String reason, String domain) {
        super(statusMessage);

        this.statusCode = statusCode;
        this.reason = reason;
        this.domain = domain;
    }

    protected BaseResponseException(int statusCode, String statusMessage, String reason, String domain, Throwable cause) {
        super(statusMessage, cause);

        this.statusCode = statusCode;
        this.reason = reason;
        this.domain = domain;
    }
}
