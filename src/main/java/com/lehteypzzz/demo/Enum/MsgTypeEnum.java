package com.lehteypzzz.demo.Enum;

public enum MsgTypeEnum {
    SINGLE(1, "单聊信息"),
    GROUP(2, "群聊消息");
    public final Integer type;
    public final String content;
    MsgTypeEnum(Integer type, String content){
        this.type = type;
        this.content = content;
    }
}
