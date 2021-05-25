package com.progect.GrassCutterShop.entity;

import com.progect.GrassCutterShop.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable, Entity {
    private Long userId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private UserRole role;
}
