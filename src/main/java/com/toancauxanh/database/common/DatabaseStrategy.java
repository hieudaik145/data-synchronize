package com.toancauxanh.database.common;

import java.sql.Connection;
import java.util.List;

import com.toancauxanh.database.entity.InfoColumnDto;
import com.toancauxanh.database.entity.InfoDatabaseDto;
import com.toancauxanh.database.entity.InfoTableDto;


public interface DatabaseStrategy {
    
    List<InfoTableDto> getInfoTables(InfoDatabaseDto infoDatabase);
    
    List<InfoColumnDto> getInfoColumns(InfoTableDto infoTable, Connection conn);
    
}
