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

import java.util.ArrayList;
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
    @RequestMapping("/createGroup")
    @ResponseBody
    public String createGroup(String groupName, Integer meId, String checkboxes){
        String[] ids=checkboxes.split(",");
        List<Integer> intIds=new ArrayList<>();
        for(String i:ids)
        {
            intIds.add(Integer.valueOf(i));
        }
        Group g = new Group();
        Group group = groupService.findByGroupName(groupName);
        if(group==null){
            g.setGroupName(groupName);
            g.setGroupOwnerId(meId);
            g.setGroupPic("images/group/2.jpg");
            Integer gId = groupService.saveGroup(g).getGroupId();
            groupRelService.saveGroupRel(gId, meId);
            for(Integer id: intIds){
                groupRelService.saveGroupRel(gId,id);
            }
            return "创建成功";
        }
        else{
            return "群名称已存在，创建失败";
        }
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
    @RequestMapping("/deleteFriend")
    @ResponseBody
    public String deleteFriend(Integer friendId, Integer meId){
        friendshipService.deleteOne(friendId,meId);
        return "";
    }

    @RequestMapping("/GroupAdd")
    @ResponseBody
    public GroupRel addGroup(Integer groupId, Integer userId){
        return groupRelService.saveGroupRel(groupId, userId);
    }


    @RequestMapping("/deleteGroup")
    @ResponseBody
    public String deleteGroup(Integer groupId, Integer meId){
        groupRelService.deleteOne(groupId, meId);
        return "";
    }

    @RequestMapping("updateUserInfo")
    @ResponseBody
    public User updateUserInfo(Integer userId, String userName, String password){
        User user = userService.findOneByUserId(userId);
        System.out.println(user.toString());
        return userService.updateUser(user, userName, password);
    }
}
