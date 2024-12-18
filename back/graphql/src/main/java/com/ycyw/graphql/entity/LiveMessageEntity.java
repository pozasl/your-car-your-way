package com.ycyw.graphql.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("live_message")
public class LiveMessageEntity {

    @Id
    Long id;

    Long fromUserId;
    @Transient
    private AccountEntity fromUser;

    Long toUserId;
    @Transient
    private AccountEntity toUser;

    LocalDateTime createdAt;

    String content;
    
}