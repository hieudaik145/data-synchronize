package com.toancauxanh.database.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class InfoTableDto {
    
    @Id
    @GeneratedValue
    private int id;
    
    private String tableSchema;
    
    private String tableName;

    private List<InfoColumnDto> infoColumns;
}
