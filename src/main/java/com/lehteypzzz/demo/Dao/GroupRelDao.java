package com.lehteypzzz.demo.Dao;

import com.lehteypzzz.demo.Entity.GroupRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRelDao extends JpaRepository<GroupRel, Integer> {
    List<GroupRel> findAllByGroupMemberId(Integer userId);
    List<GroupRel> findAllByGroupId(Integer groupId);

}
