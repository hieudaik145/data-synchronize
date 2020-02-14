package com.toancauxanh.synchronizedata.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.toancauxanh.common.Constant;
import com.toancauxanh.common.dao.ConnectionDAO;
import com.toancauxanh.database.entity.InfoColumn;
import com.toancauxanh.database.entity.InfoDatabase;
import com.toancauxanh.database.entity.InfoTable;
import com.toancauxanh.synchronizedata.dao.DataSynchronizedDAO;

public class SynchronizeDataService {

    ConnectionDAO connectionDAO = new ConnectionDAO();

    DataSynchronizedDAO dataSynchronizedDAO = new DataSynchronizedDAO();

    /**
     * Method synchronize all database source to database staging
     * 
     * @param infoDatabases
     * @throws SQLException
     */
    public void synchronizeListDatabase(List<InfoDatabase> infoDatabases) throws SQLException {

        for (InfoDatabase infoDatabase : infoDatabases) {
            synchronizeDatabase(infoDatabase);
        }

    }

    /**
     * Method synchronize database of database source to database staging
     * 
     * @param infoDatabase
     * @throws SQLException
     */
    public void synchronizeDatabase(InfoDatabase infoDatabase) {

        Connection connSourceData = connectionDAO.getConnection(infoDatabase);

        // for each info table of info database and synchronize table
        for (InfoTable infoTable : infoDatabase.getInfoTables()) {
            synchronizeTable(connSourceData, infoTable);
        }
        // close connecting database source if not null
        try {
            connSourceData.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method synchronize table of database source to database staging
     * 
     * @param connSourceData
     * @param infoTable
     * @throws SQLException
     */
    private void synchronizeTable(Connection connSourceData, InfoTable infoTable) {

        // create connection to database staging
        Connection connStaging = connectionDAO.getConnectionStaging();

        // call get list data of table from database source target
        List<Map<String, Object>> datas = getListDataInsertPreInsertFromTable(infoTable, connSourceData);

        // partition list data threshold 1000
        List<List<Map<String, Object>>> parition = Lists.partition(datas, 1000);

        // call batch insert to database staging
        for (List<Map<String, Object>> list : parition) {
            dataSynchronizedDAO.batchInsertDataSourceToDataStagin(list, buildStringQueryInsert(infoTable), infoTable,
                    connStaging);
        }

        // if connection staging not null close it
        if (connStaging != null) {
            try {
                connStaging.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Build query string insert to database staging
     * 
     * @param infoTable
     * @return stringQuery
     */
    private String buildStringQueryInsert(InfoTable infoTable) {

        // Using string builder to create query insert string
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("INSERT INTO " + infoTable.getTableName() + Constant.SPACE + "(");

        for (InfoColumn infoColumn : infoTable.getInfoColumns()) {

            if (infoTable.getInfoColumns().size() == infoTable.getInfoColumns().lastIndexOf(infoColumn)) {

                stringBuilder.append(infoColumn.getColumnName() + ") VALUES (");
            } else {
                stringBuilder.append(infoColumn.getColumnName() + Constant.SPACE + Constant.COMMA);
            }

        }

        for (int i = 0; i < infoTable.getInfoColumns().size(); i++) {
            if (i == infoTable.getInfoColumns().size() - 1) {
                stringBuilder.append(Constant.ASK + ")");
            } else {
                stringBuilder.append(Constant.ASK + Constant.COMMA);
            }

        }

        return stringBuilder.toString();

    }

    /**
     * Get List database from single table
     * 
     * @param infoTable
     * @param conn
     * @return
     * @throws SQLException
     */
    private List<Map<String, Object>> getListDataInsertPreInsertFromTable(InfoTable infoTable, Connection conn) {

        // Using string builder to create query String
        StringBuilder queryString = new StringBuilder("SELECT ");

        for (InfoColumn column : infoTable.getInfoColumns()) {

            if (infoTable.getInfoColumns().size() == infoTable.getInfoColumns().lastIndexOf(column)) {
                queryString.append(column.getColumnName() + Constant.SPACE + "FROM " + infoTable.getTableName());
            } else {
                queryString.append(column.getColumnName() + Constant.COMMA + Constant.SPACE);
            }
        }

        // call select data from source database
        ResultSet rs = dataSynchronizedDAO.getResultSetFromDataSource(queryString.toString(), conn);

        if (rs == null) {
            return Collections.emptyList();
        }

        return buildListObjectPreInsertFromResultSet(rs, infoTable);

    }

    /**
     * Build list value for result set get from databases source
     * 
     * @param resultSet
     * @param infoTable
     * @return List value insert to database
     * @throws SQLException
     */
    private List<Map<String, Object>> buildListObjectPreInsertFromResultSet(ResultSet resultSet, InfoTable infoTable) {

        List<Map<String, Object>> target = new ArrayList<>();

        try {
            while (resultSet.next()) {

                Map<String, Object> map = new HashMap<>();

                for (InfoColumn infoColumn : infoTable.getInfoColumns()) {
                    map.put(infoColumn.getColumnName(), resultSet.getObject(infoColumn.getColumnName()));
                }
                target.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return target;
    }
}
