package com.lehteypzzz.demo.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="GROUPS")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Integer groupId;

    @Column(name = "GROUP_OWNER_ID")
    private Integer groupOwnerId;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @Column(name = "GROUP_PIC")
    private String groupPic;
}
