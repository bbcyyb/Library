package dev.kevinyu.service.restful.model;

public final class ResultEntity {

    private ResultEntity(int statusCode, String errorMessage, Object data) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    private int statusCode;

    private String errorMessage;

    private Object data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultEntity success(Object data) {

        return new ResultEntity(200, null, data);
    }

    public static ResultEntity success(int statusCode, Object data) {
        return new ResultEntity(statusCode, null, data);
    }

    public static ResultEntity fail(String message) {
        return new ResultEntity(500, message, null);
    }

    public static ResultEntity fail(int statusCode, String message) {
        return new ResultEntity(statusCode, message, null);
    }

    public static ResultEntity fail(int statusCode, String message, Object data) {
        return new ResultEntity(statusCode, message, data);
    }
}
