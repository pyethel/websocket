package com.lehteypzzz.demo.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FRIENDSHIP")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRIENDSHIP_ID")
    private Integer friendshipId;
    @Column(name = "FRIEND_ID")
    private Integer friendId;
    @Column(name = "USER_ID")
    private Integer userId;
}
