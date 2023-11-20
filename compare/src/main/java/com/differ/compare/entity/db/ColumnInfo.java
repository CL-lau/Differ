package com.differ.compare.entity.db;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/3 0:00
 */
@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "column name cannot be null")
    private String columnName;
    private String type;
    private String description;

    @NotBlank(message = "table name cannot be null")
    private String tableName;

    @NotBlank(message = "database name cannot be null")
    private String databaseName;

    // Getters and setters
}

