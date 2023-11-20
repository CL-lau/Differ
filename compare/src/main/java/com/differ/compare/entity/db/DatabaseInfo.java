package com.differ.compare.entity.db;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/2 23:43
 */

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String host;
    private int port;

    @NotBlank(message = "database name cannot be null")
    private String databaseName;
    private String url;
    private String username;
    private String password;
    private List<TableInfo> tables;

}

