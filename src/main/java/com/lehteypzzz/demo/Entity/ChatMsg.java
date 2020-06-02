package com.lehteypzzz.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MESSAGE")
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 5919985739643171853L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private int msgId;

    @Column(name = "SENDER_ID")
    private int senderId;

    @Column(name = "SENDER_NAME")
    private String senderName;

    @Column(name = "SENDER_PIC")
    private String senderPic;

    @Column(name = "RECEIVER_ID")
    private int receiverId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "SEND_TIME")
    private LocalDateTime localDateTime;

    @Column(name = "MSG")
    private String msg;

    @Column(name = "TYPE")
    private Integer type;

    @Transient
    private Integer act;
}
