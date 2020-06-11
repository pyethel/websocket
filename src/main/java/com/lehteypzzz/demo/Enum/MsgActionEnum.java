package com.lehteypzzz.demo.Enum;

public enum MsgActionEnum {
    CONNECT(1, "第一次初始化连接"),
    CHAT(2, "聊天消息");

    public final Integer type;
    public final String content;
    MsgActionEnum(Integer type, String content){
        this.type = type;
        this.content = content;
    }
}
