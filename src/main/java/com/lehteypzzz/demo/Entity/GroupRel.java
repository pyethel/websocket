package com.lehteypzzz.demo.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "GROUP_REL")
public class GroupRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="GROUP_REL_ID")
    private Integer groupRelId;

    @Column(name ="GROUP_ID")
    private Integer groupId;

    @Column(name = "GROUP_MEMBER_ID")
    private Integer groupMemberId;
}
