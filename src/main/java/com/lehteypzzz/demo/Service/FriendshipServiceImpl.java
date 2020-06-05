package com.lehteypzzz.demo.Service;

import com.lehteypzzz.demo.Dao.FriendshipDao;
import com.lehteypzzz.demo.Entity.Friendship;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipServiceImpl {
    final FriendshipDao friendshipDao;

    public FriendshipServiceImpl(FriendshipDao friendshipDao) {
        this.friendshipDao = friendshipDao;
    }

    public List<Integer> getFriendshipIdByUserId(Integer userId) {
        List<Friendship> friendList = friendshipDao.findAllByUserId(userId);
        List<Integer> friendIdList = new ArrayList<>();
        for (Friendship f : friendList) {
            friendIdList.add(f.getFriendId());
        }
        return friendIdList;
    }

    public void saveOne(Integer userId, Integer friendId){
        Friendship friendship = new Friendship();
        friendship.setUserId(userId);
        friendship.setFriendId(friendId);
        friendshipDao.save(friendship);
    }
    public void deleteOne(Integer friendId, Integer userId){
        Friendship rel1 = friendshipDao.findByFriendIdAndUserId(friendId, userId);
        Friendship rel2 = friendshipDao.findByFriendIdAndUserId(userId, friendId);
        friendshipDao.delete(rel1);
        friendshipDao.delete(rel2);
    }
}
