package com.lehteypzzz.demo.Controller;

import com.lehteypzzz.demo.Entity.ChatMsg;
import com.lehteypzzz.demo.Entity.Group;
import com.lehteypzzz.demo.Entity.User;
import com.lehteypzzz.demo.Service.ChatMsgServiceImpl;
import com.lehteypzzz.demo.Service.GroupRelServiceImpl;
import com.lehteypzzz.demo.Service.GroupServiceImpl;
import com.lehteypzzz.demo.Service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {
    final
    UserServiceImpl userServiceImpl;
    final
    ChatMsgServiceImpl chatMsgServiceImpl;
    final
    GroupServiceImpl groupServiceImpl;
    final
    GroupRelServiceImpl groupRelServiceImpl;

    public ChatController(UserServiceImpl userServiceImpl,
                          ChatMsgServiceImpl chatMsgServiceImpl,
                          GroupServiceImpl groupServiceImpl,
                          GroupRelServiceImpl groupRelServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.chatMsgServiceImpl = chatMsgServiceImpl;
        this.groupServiceImpl = groupServiceImpl;
        this.groupRelServiceImpl = groupRelServiceImpl;
    }

    @RequestMapping("/SingleChatHistory")
    @ResponseBody
    public List<ChatMsg> singleChatHistory(Integer senderId, Integer receiverId, Integer msgType){
        return chatMsgServiceImpl.getSingleChatHistory(senderId, receiverId, msgType);
    }
    @RequestMapping("/GroupChatHistory")
    @ResponseBody
    public List<ChatMsg> groupChatHistory(Integer receiverId, Integer msgType){
        return chatMsgServiceImpl.getGroupChatHistory(receiverId, msgType);
    }

    @RequestMapping("/getFriendInfo")
    @ResponseBody
    public User getFriendInfo(Integer friendId){
        return userServiceImpl.findOneByUserId(friendId);
    }

    @RequestMapping("/getGroupInfo")
    @ResponseBody
    public Group getGroupInfo(Integer groupId){
        return groupServiceImpl.findGroupById(groupId);
    }
}
