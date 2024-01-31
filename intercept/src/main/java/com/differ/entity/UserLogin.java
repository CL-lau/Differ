package com.differ.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/30 16:14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    private int id;
    private String username;
    private String password;
}
