package com.differ.entity;

import lombok.*;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/20 14:13
 */

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String sex;
    private String pwd;
    private String email;
}
