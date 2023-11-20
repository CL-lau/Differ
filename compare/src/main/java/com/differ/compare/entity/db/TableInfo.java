package com.differ.compare.entity.db;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/2 23:59
 */
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "table name cannot be null")
    private String tableName;
    private String description;

    // 表中的字段信息，你可以使用List或其他数据结构来表示字段
    private List<ColumnInfo> columns;

    @NotBlank(message = "database name cannot be null")
    private String databaseName;

    // Getters and setters
}
