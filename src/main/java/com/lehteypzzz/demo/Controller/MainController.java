package com.lehteypzzz.demo.Controller;

import com.lehteypzzz.demo.Entity.Group;
import com.lehteypzzz.demo.Entity.User;
import com.lehteypzzz.demo.Service.GroupRelServiceImpl;
import com.lehteypzzz.demo.Service.GroupServiceImpl;
import com.lehteypzzz.demo.Service.UserServiceImpl;
import com.lehteypzzz.demo.Service.FriendshipServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    final
    UserServiceImpl userServiceImpl;
    final
    FriendshipServiceImpl friendshipServiceImpl;
    final
    GroupRelServiceImpl groupRelServiceImpl;
    final
    GroupServiceImpl groupServiceImpl;
    public MainController(UserServiceImpl userServiceImpl,
                          FriendshipServiceImpl friendshipServiceImpl,
                          GroupRelServiceImpl groupRelServiceImpl,
                          GroupServiceImpl groupServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.friendshipServiceImpl = friendshipServiceImpl;
        this.groupRelServiceImpl = groupRelServiceImpl;
        this.groupServiceImpl = groupServiceImpl;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping("/AddFriend")
    public String AddFriend(){ return "addFriend"; }
    @RequestMapping("/AddGroup")
    public String AddGroup(){ return "addGroup"; }
    @RequestMapping("/FriendList")
    public String FriendList(){ return "friendList"; }
    @RequestMapping("/GroupList")
    public String GroupList(){ return "groupList"; }
    @RequestMapping("/UpdateInfo")
    public String UpdateInfo(){ return "updateInfo"; }
    @RequestMapping("/SingleChat")
    public String SingleChat(){ return "singleChat"; }
    @RequestMapping("/GroupChat")
    public String GroupChat(){ return "groupChat"; }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Map<String, Object> doLogin(String userName, String password){
        Map<String, Object> map = new HashMap<>();
        System.out.println(userName+" "+password);
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
            map.put("error","用户名或密码不能为空");
            System.out.println("用户名或密码不能为空");
            return map;
        }
        boolean userNameIsExist = userServiceImpl.queryUserNameIsExist(userName);
        // 用户名存在为true 可以登录
        User result;
        if(userNameIsExist){
            result = userServiceImpl.getUserByUserName(userName);
            if(!result.getPassword().equals(password)){
                map.put("error","用户名或密码不正确");
                System.out.println("用户名或密码不正确");
            }else{
                // 登录成功  修改在线状态
//                userServiceImpl.updateIsOnline(result, IsOnlineEnum.ONLINE.type);
                map.put("user",result);
            }
        }
        return map;
    }

    @RequestMapping("doRegister")
    public String doRegister(String userName, String password, Model model){
        if(!userServiceImpl.queryUserNameIsExist(userName)) {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setPicture("images/user/3.jpg");
            userServiceImpl.saveUser(user);
            return "login";
        }
        else {
            model.addAttribute("error","用户名已存在！请重新注册");
            return "register";
        }
    }


    /**
     *
     * @param userId 用户id
     * @return  群组列表
     */
    @RequestMapping("/groupList")
    @ResponseBody
    public List<Group> groupList(Integer userId)
    {
        List<Integer> groupIdList = groupRelServiceImpl.getGroupIdByGroupMemberId(userId);
        return groupServiceImpl.findGroupByIdList(groupIdList);
    }

    /**
     * @param userId 用户id
     * @return  好友列表
     */
    @RequestMapping("/friendList")
    @ResponseBody
    public List<User> friendList2(Integer userId){
        List<Integer> friendIdList = friendshipServiceImpl.getFriendshipIdByUserId(userId);
        return userServiceImpl.findFriendByIdList(friendIdList);
    }

}
