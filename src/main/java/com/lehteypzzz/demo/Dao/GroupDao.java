package com.lehteypzzz.demo.Dao;

import com.lehteypzzz.demo.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupDao extends JpaRepository<Group, Integer> {
    Group findOneByGroupId(Integer groupId);

    @Query(value = "select * from groups g where g.group_name like CONCAT('%',:groupName,'%')", nativeQuery = true)
    List<Group> findGroupListByGroupName(@Param("groupName") String groupName);

}
