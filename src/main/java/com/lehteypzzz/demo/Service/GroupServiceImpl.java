package com.lehteypzzz.demo.Service;

import com.lehteypzzz.demo.Dao.GroupDao;
import com.lehteypzzz.demo.Entity.Group;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl {
    final GroupDao groupDao;
    public GroupServiceImpl(GroupDao groupDao){
        this.groupDao = groupDao;
    }

    public List<Group> findGroupByIdList(List<Integer> groupIdList){
        List<Group> groupList = new ArrayList<>();
        for(Integer id:groupIdList){
            groupList.add(groupDao.findOneByGroupId(id));
        }
        return groupList;
    }

    public Group findGroupById(Integer groupId){
        return groupDao.findOneByGroupId(groupId);
    }

    public List<Group> getGroupListByGroupName(String groupName){
        return groupDao.findGroupListByGroupName(groupName);
    }

    public Group saveGroup(Group g){
        return groupDao.save(g);
    }

    public Group findByGroupName(String groupName){
        return groupDao.findByGroupName(groupName);
    }
}
