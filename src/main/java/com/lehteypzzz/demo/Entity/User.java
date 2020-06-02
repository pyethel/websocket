package com.lehteypzzz.demo.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER")
public class User {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "IS_ONLINE")
    private Integer isOnline;
    @Column(name = "PICTURE")
    private String picture;

}