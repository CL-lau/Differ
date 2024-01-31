package com.differ.compare.entity.debezium;

import lombok.*;

import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/5 15:44
 */
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatabaseData {
    private String name;
    private Boolean enabled;
    private String offsetPath;
    private String host;
    private String port;
    private String username;
    private String password;
    private String serverId;
    private List<String> table;
    private String historyPath;
}
