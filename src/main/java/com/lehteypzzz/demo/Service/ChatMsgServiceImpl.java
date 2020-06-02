package com.lehteypzzz.demo.Service;

import com.lehteypzzz.demo.Dao.ChatMsgDao;
import com.lehteypzzz.demo.Entity.ChatMsg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chatMsgServiceImpl")
public class ChatMsgServiceImpl {
    final ChatMsgDao chatMsgDao;

    public ChatMsgServiceImpl(ChatMsgDao chatMsgDao) {
        this.chatMsgDao = chatMsgDao;
    }

    public Integer saveMsg(ChatMsg chatMsg) {
        ChatMsg chatMsgNew = chatMsgDao.save(chatMsg);
        return chatMsgNew.getMsgId();
    }

    public List<ChatMsg> getSingleChatHistory(Integer userId, Integer friendId, Integer type){
        return chatMsgDao.getSingleChatHistoryBySenderIdAndReceiverIdAndType(userId, friendId, type);
    }

    public List<ChatMsg> getGroupChatHistory(Integer groupId, Integer type){
        return chatMsgDao.getGroupChatHistoryBySenderIdAndAndReceiverIdAndType(groupId, type);
    }
}
