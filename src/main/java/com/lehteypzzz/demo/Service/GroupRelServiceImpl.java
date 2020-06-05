package com.lehteypzzz.demo.Service;

import com.lehteypzzz.demo.Dao.GroupRelDao;
import com.lehteypzzz.demo.Dao.UserDao;
import com.lehteypzzz.demo.Entity.GroupRel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("groupRelServiceImpl")
public class GroupRelServiceImpl {
    final GroupRelDao groupRelDao;
    final UserDao userDao;
    public GroupRelServiceImpl(GroupRelDao groupRelDao,
                               UserDao userDao){
        this.groupRelDao = groupRelDao;
        this.userDao = userDao;
    }
    // 用户id找群id列表
    public List<Integer> getGroupIdByGroupMemberId(Integer userId){
        List<GroupRel> groupRelList = groupRelDao.findAllByGroupMemberId(userId);
        List<Integer> groupIdList = new ArrayList<>();
        for (GroupRel g : groupRelList) {
            groupIdList.add(g.getGroupId());
        }
        return groupIdList;
    }

    // 群id找群员id列表
    public List<Integer> getGroupMemberIdByGroupId(Integer groupId, Integer senderId){
        List<GroupRel> groupMemberList = groupRelDao.findAllByGroupId(groupId);
        List<Integer> groupMemberIdList = new ArrayList<>();
        for(GroupRel g : groupMemberList){
            if(g.getGroupMemberId() == senderId)
                continue;
            groupMemberIdList.add(g.getGroupMemberId());
        }
        return groupMemberIdList;
    }

    public GroupRel saveGroupRel(Integer groupId, Integer userId){
        GroupRel g = new GroupRel();
        g.setGroupId(groupId);
        g.setGroupMemberId(userId);
        return groupRelDao.save(g);
    }
    public void deleteOne(Integer groupId, Integer meId){
        GroupRel g = groupRelDao.findByGroupIdAndGroupMemberId(groupId, meId);
        groupRelDao.delete(g);
    }
}
