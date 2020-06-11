package com.lehteypzzz.demo.Netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @Description: 用户id和channel的关联关系处理
 */
public class UserChannelRel {
    private static final HashMap<Integer, Channel> manager = new HashMap<>();
    public static void put(Integer sendId, Channel channel){
        manager.put(sendId, channel);
    }
    public static Channel get(Integer sendId){
        return manager.get(sendId);
    }

    private static final HashMap<Channel, Integer> online = new HashMap<>();
    public static void put2(Channel channel, Integer sendId){
        online.put(channel, sendId);
    }
    public static Integer get2(Channel channel){
        return online.get(channel);
    }
}
