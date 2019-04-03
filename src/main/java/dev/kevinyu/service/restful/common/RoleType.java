package dev.kevinyu.service.restful.common;

import java.util.Arrays;

public enum RoleType {

    User(1, "User"),
    Admin(2, "Admin");

    RoleType(int code, String description) {
        this.code = code;
        this.name = description;
    }

    private int code;

    private String name;

    public static RoleType valueOf(int code) {
        for(RoleType r : RoleType.values()){
            if(r.code == code) {}
        }

        return Arrays.stream(RoleType.values()).filter(r -> r.code == code).findFirst().get();
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
