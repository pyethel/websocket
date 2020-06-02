package com.lehteypzzz.demo.Dao;

import com.lehteypzzz.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findOneByUserName(String userName);
    User findOneByUserId(Integer userId);

    @Query(value = "select * from user u where u.user_name like CONCAT('%',:userName,'%')", nativeQuery = true)
    List<User> findUserListByUserName(@Param("userName") String userName);

}
