package com.lehteypzzz.demo.Controller;

import com.lehteypzzz.demo.Entity.Group;
import com.lehteypzzz.demo.Entity.GroupRel;
import com.lehteypzzz.demo.Entity.User;
import com.lehteypzzz.demo.Service.FriendshipServiceImpl;
import com.lehteypzzz.demo.Service.GroupRelServiceImpl;
import com.lehteypzzz.demo.Service.GroupServiceImpl;
import com.lehteypzzz.demo.Service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class InfoController {
    final
    FriendshipServiceImpl friendshipService;
    final
    UserServiceImpl userService;
    final
    GroupServiceImpl groupService;
    final
    GroupRelServiceImpl groupRelService;
    public InfoController(UserServiceImpl userService,
                          FriendshipServiceImpl friendshipService,
                          GroupServiceImpl groupService,
                          GroupRelServiceImpl groupRelService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.groupService = groupService;
        this.groupRelService = groupRelService;
    }

    @RequestMapping("/searchFriend")
    @ResponseBody
    public List<User> searchFriend(String friendName, Integer meId){
        List<User> queryUsers = userService.getUserListByUserName(friendName);
        User me = userService.findOneByUserId(meId);
        List<Integer> friendsIds = friendshipService.getFriendshipIdByUserId(meId);
        List<User> myFriends= userService.findFriendByIdList(friendsIds);
        queryUsers.removeIf(user -> user == me);
        queryUsers.removeAll(myFriends);
        return queryUsers;

    }

    @RequestMapping("/searchGroup")
    @ResponseBody
    public List<Group> searchGroup(String groupName, Integer meId){
        List<Group> groups = groupService.getGroupListByGroupName(groupName);
        List<Integer> myGroupsIds = groupRelService.getGroupIdByGroupMemberId(meId);
        List<Group> myGroups = groupService.findGroupByIdList(myGroupsIds);
        groups.removeAll(myGroups);
        return groups;
    }
    @RequestMapping("/friendAdd")
    @ResponseBody
    public String addFriend(Integer friendId, Integer userId){
        friendshipService.saveOne(userId, friendId);
        friendshipService.saveOne(friendId, userId);
        return "添加好友成功";
    }

    @RequestMapping("/GroupAdd")
    @ResponseBody
    public GroupRel addGroup(Integer groupId, Integer userId){
        return groupRelService.saveGroupRel(groupId, userId);
    }

    @RequestMapping("updateUserInfo")
    @ResponseBody
    public User updateUserInfo(Integer userId, String userName, String password){
        User user = userService.findOneByUserId(userId);
        System.out.println(user.toString());
        return userService.updateUser(user, userName, password);
    }
}
