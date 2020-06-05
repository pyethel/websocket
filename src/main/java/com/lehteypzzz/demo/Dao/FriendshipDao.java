package com.lehteypzzz.demo.Dao;

import com.lehteypzzz.demo.Entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipDao extends JpaRepository<Friendship, Integer> {
    List<Friendship> findAllByUserId(Integer userId);

    Friendship findByFriendIdAndUserId(Integer friendId, Integer userId);
}
