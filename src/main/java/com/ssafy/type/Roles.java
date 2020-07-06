package com.ssafy.type;

public enum Roles {
    ROLE_BUYER("ROLE_BUYER"), ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN"), ROLE_SELLER("ROLE_SELLER");
    public String NAME;
    Roles(String NAME){
        this.NAME = NAME;
    }
}
