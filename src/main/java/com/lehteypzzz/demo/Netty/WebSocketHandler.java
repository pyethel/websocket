package com.lehteypzzz.demo.Netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lehteypzzz.demo.Enum.IsOnlineEnum;
import com.lehteypzzz.demo.Enum.MsgActionEnum;
import com.lehteypzzz.demo.Enum.MsgTypeEnum;
import com.lehteypzzz.demo.Utils.SpringUtil;
import com.lehteypzzz.demo.Entity.ChatMsg;
import com.lehteypzzz.demo.Entity.User;
import com.lehteypzzz.demo.Service.ChatMsgServiceImpl;
import com.lehteypzzz.demo.Service.GroupRelServiceImpl;
import com.lehteypzzz.demo.Service.UserServiceImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 接收/处理/响应客户端websocket请求的核心业务处理类
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 文本信息处理
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 1.获取消息
        Channel currentChannel = ctx.channel();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        String content =  msg.text();
        // json字符串转msg对象
        ChatMsg chatMsg = mapper.readValue(content, ChatMsg.class);
        Integer action = chatMsg.getAct();
        Integer type = chatMsg.getType();
        // 2.判断消息类型
        //  2.1 初始化channel，把用的channel与userId关联
        if(action == MsgActionEnum.CONNECT.type){
            Integer sendId = chatMsg.getSenderId();
            UserChannelRel.put(sendId, currentChannel);
            UserChannelRel.put2(currentChannel, sendId);
            UserServiceImpl userServiceimpl =
                    (UserServiceImpl)SpringUtil.getBean("userServiceImpl");
            User user = userServiceimpl.findOneByUserId(sendId);
            userServiceimpl.updateIsOnline(user, IsOnlineEnum.ONLINE.type);
        }
        //  2.2 聊天类型的信息，聊天记录保存到数据库
        else if(action == MsgActionEnum.CHAT.type){
            LocalDateTime time = LocalDateTime.now();
            chatMsg.setLocalDateTime(time);
            // 保存数据
            // SpringUtil工具类：普通java类使用service类
            ChatMsgServiceImpl chatMsgServiceimpl =
                    (ChatMsgServiceImpl)SpringUtil.getBean("chatMsgServiceImpl");
            chatMsgServiceimpl.saveMsg(chatMsg);
            // 发送消息
            //  单聊
            if(type == MsgTypeEnum.SINGLE.type){
                // 给发送者发送
                currentChannel.writeAndFlush(
                        new TextWebSocketFrame(mapper.writeValueAsString(chatMsg)));
                // 接收者id
                Integer receiverId = chatMsg.getReceiverId();
                // HashMap中取出接收者Channel
                Channel receiverChannel = UserChannelRel.get(receiverId);
                if(receiverChannel!=null){
                    // channel不为空 即为在线
                    Channel findChannel = users.find(receiverChannel.id());
                    if(findChannel!=null){
                        // 给接收者发送
                        receiverChannel.writeAndFlush(
                                new TextWebSocketFrame(mapper.writeValueAsString(chatMsg)));
                    }
                }
            }
            //  群聊
            else if(type == MsgTypeEnum.GROUP.type){
                // 群id
                Integer groupId = chatMsg.getReceiverId();
                // 给发送者发送
                currentChannel.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(chatMsg)));
                // 查询群员id
                GroupRelServiceImpl groupRelService =
                        (GroupRelServiceImpl)SpringUtil.getBean("groupRelServiceImpl");
                List<Integer> groupMemberIdList =
                        groupRelService.getGroupMemberIdByGroupId(groupId, chatMsg.getSenderId());
                for(Integer id:groupMemberIdList){
                    // 取出channel 遍历发送
                    Channel groupReceiverChannel = UserChannelRel.get(id);
                    if(groupReceiverChannel!=null){
                        // channel不为空 即为在线
                        Channel findChannel = users.find(groupReceiverChannel.id());
                        if(findChannel!=null){
                            // 给接受者发送
                            groupReceiverChannel.writeAndFlush(
                                    new TextWebSocketFrame(mapper.writeValueAsString(chatMsg)));
                        }
                    }
                }
            }
        }
    }
    // 上线处理
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        System.out.println("客户端连接对应短id"+ctx.channel().id().asShortText());
    }
    // 下线处理
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        /*
         *  断开连接 用户状态设为离线
         */
        Integer offlineUserId = UserChannelRel.get2(ctx.channel());
        UserServiceImpl userServiceimpl = (UserServiceImpl)SpringUtil.getBean("userServiceImpl");
        User user = userServiceimpl.findOneByUserId(offlineUserId);
        userServiceimpl.updateIsOnline(user, IsOnlineEnum.OFFLINE.type);

        users.remove(ctx.channel());
        System.out.println("客户端断开对应短id"+ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception{
        cause.printStackTrace();
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
