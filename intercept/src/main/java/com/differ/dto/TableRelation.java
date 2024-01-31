package com.differ.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/27 23:34
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class TableRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long flag;
    private String values;
    private String tables;

    public TableRelation(long flag, String values, String tables){
        this.flag = flag;
        this.values = values;
        this.tables = tables;
    }
}
