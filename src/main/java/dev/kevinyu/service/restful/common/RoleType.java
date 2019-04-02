package dev.kevinyu.service.restful.common;

public enum RoleType {

    User(1, "User"),
    Admin(2, "Admin");

    RoleType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;

    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
