package com.lehteypzzz.demo.Dao;


import com.lehteypzzz.demo.Entity.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChatMsgDao extends JpaRepository<ChatMsg, Integer> {
    @Query(value = "select * from message m where (m.sender_id = ?1 and m.receiver_id = ?2) or (m.sender_id = ?2 and m.receiver_id = ?1) and m.type = ?3", nativeQuery = true)
    List<ChatMsg> getSingleChatHistoryBySenderIdAndReceiverIdAndType(Integer senderId, Integer receiverId, Integer msgType);

    @Query(value = "select * from message m where m.receiver_id = ?1 and m.type = ?2", nativeQuery = true)
    List<ChatMsg> getGroupChatHistoryBySenderIdAndAndReceiverIdAndType(Integer receiverId, Integer msgType);
}
