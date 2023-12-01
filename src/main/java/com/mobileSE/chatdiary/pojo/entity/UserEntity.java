package com.mobileSE.chatdiary.pojo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String username;

    @NotNull
    private String password;
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255) DEFAULT '用户啥也没写'")
    private String userInfo;

    private Long avatarUrlId;
}
