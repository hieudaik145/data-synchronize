package com.toancauxanh.synchronizedata.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.toancauxanh.database.common.DatabaseStrategy;
import com.toancauxanh.database.common.MySQLDAO;
import com.toancauxanh.database.common.OracleSQLDAO;
import com.toancauxanh.database.common.SQLServerDAO;
import com.toancauxanh.database.entity.DatabaseType;
import com.toancauxanh.database.entity.InfoDatabaseDto;
import com.toancauxanh.database.entity.InfoTableDto;
import com.toancauxanh.synchronizedata.dao.DataSynchronizedDAO;

public class SynchronizeDataService {

    DataSynchronizedDAO dataSynchronizedDAO = new DataSynchronizedDAO();

    /**
     * Method synchronize all database source to database staging
     * 
     * @param infoDatabases
     * @throws SQLException
     */
    public void synchronizeListDatabase(List<InfoDatabaseDto> infoDatabases) {

        for (InfoDatabaseDto infoDatabase : infoDatabases) {
            synchronizeDatabase(infoDatabase);
        }

    }

    /**
     * Method perform synchronize database of database source to database staging
     * 
     * @param infoDatabase
     */
    public void synchronizeDatabase(InfoDatabaseDto infoDatabase) {

        DatabaseStrategy databaseStrategy = null;

        // check database type and new 
        if (infoDatabase.getTypeDB() == DatabaseType.MYSQL || infoDatabase.getTypeDB() == DatabaseType.MARIA_DB) {
            databaseStrategy = new MySQLDAO();
        } else if (infoDatabase.getTypeDB() == DatabaseType.MS_SQL_SERVER) {
            databaseStrategy = new SQLServerDAO();
        } else {
            databaseStrategy = new OracleSQLDAO();
        }
        // find all info table anh info column
        infoDatabase.setInfoTables(databaseStrategy.getInfoTables(infoDatabase));

        // get all data
        Map<InfoTableDto, List<Map<String, Object>>> dataDatabase = getAllDataOfListTable(infoDatabase);

        Set<InfoTableDto> keySet = dataDatabase.keySet();

        for (InfoTableDto infoTableDto : keySet) {

            List<Map<String, Object>> datas = dataDatabase.get(infoTableDto);

            List<List<Map<String, Object>>> data = Lists.partition(datas, 50000);

            // insert to database staging
            data.forEach(x -> dataSynchronizedDAO.batchInsertToDataStaging(x, infoTableDto));
        }
    }

    /**
     * Get all data of table
     * 
     * @param infoTableDtos
     * @param conn
     * @return Map<InfoTableDto, List<Map<String, Object>>> infotable key and datas
     *         of info table
     */
    private Map<InfoTableDto, List<Map<String, Object>>> getAllDataOfListTable(InfoDatabaseDto infoDatabaseDto) {

        Map<InfoTableDto, List<Map<String, Object>>> mapTableData = new HashMap<>();

        for (InfoTableDto infoTableDto : infoDatabaseDto.getInfoTables()) {
            List<Map<String, Object>> dataTable = dataSynchronizedDAO.getAllDataOfTable(infoTableDto, infoDatabaseDto);
            mapTableData.put(infoTableDto, dataTable);

        }

        return mapTableData;

    }
    
    /**
     * Get all data of table condition date create
     * 
     * @param infoTableDtos
     * @param conn
     * @return Map<InfoTableDto, List<Map<String, Object>>> infotable key and datas
     *         of info table
     */
    public Map<InfoTableDto, List<Map<String, Object>>> getAllDataOfListTable(InfoDatabaseDto infoDatabaseDto, Date tuNgay, Date denNgay) {

        Map<InfoTableDto, List<Map<String, Object>>> mapTableData = new HashMap<>();

        for (InfoTableDto infoTableDto : infoDatabaseDto.getInfoTables()) {
            List<Map<String, Object>> dataTable = dataSynchronizedDAO.getDataWithCondition(infoTableDto, infoDatabaseDto,tuNgay,denNgay);
            mapTableData.put(infoTableDto, dataTable);

        }

        return mapTableData;

    }
    
}
