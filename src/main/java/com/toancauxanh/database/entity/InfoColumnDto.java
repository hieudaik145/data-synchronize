package com.toancauxanh.database.entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author hieuvv
 *
 */
@Data
public class InfoColumnDto {
    
    @Id
    @GeneratedValue
    private int id;
    
    private String tableSchema;
    
    private String tableName;
    
    private String columnName;
    
}
