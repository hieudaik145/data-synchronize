package com.toancauxanh.database.common;

import java.sql.Connection;
import java.util.List;

import com.toancauxanh.database.entity.InfoColumn;
import com.toancauxanh.database.entity.InfoDatabase;
import com.toancauxanh.database.entity.InfoTable;


public interface DatabaseDAOStragery {
    
    List<InfoTable> getInfoTables(InfoDatabase infoDatabase, Connection conn);
    
    List<InfoColumn> getInfoColumns(InfoTable infoTable, Connection conn);
    

}
