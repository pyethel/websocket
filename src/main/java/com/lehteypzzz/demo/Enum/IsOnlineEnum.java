package com.lehteypzzz.demo.Enum;

public enum IsOnlineEnum {
    ONLINE(1, "在线"),
    OFFLINE(0, "离线");

    public final Integer type;
    public final String content;
    IsOnlineEnum(Integer type, String content){
        this.type = type;
        this.content = content;
    }
}
