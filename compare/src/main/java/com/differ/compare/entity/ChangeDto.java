package com.differ.compare.entity;

import com.differ.compare.entity.enumer.ServiceType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/6 19:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChangeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String host;
    private Integer port;
    private String databaseName;
    private String tableName;
    private Integer columnId;
    private String columnName;
    private String oldData;
    private String novelData;
    private String dataType;
    private Long es;
    private Long ts;
    private String sqlType;
    private ServiceType serviceType;
}
