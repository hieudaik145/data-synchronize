package com.toancauxanh.database.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toancauxanh.database.entity.InfoColumn;
import com.toancauxanh.database.entity.InfoDatabase;
import com.toancauxanh.database.entity.InfoTable;

public class OracleSQLDAO implements DatabaseDAOStragery {

    /**
     * Syntax of Oracle get list info tables from input info database
     * 
     * @param infoDatabase
     * @param conn
     * @return List info Table of Database
     */
    @Override
    public List<InfoTable> getInfoTables(InfoDatabase infoDatabase, Connection conn) {

        String sql = "SELECT TABLE_CATALOG, TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_CATALOG = ?";

        List<InfoTable> infoTables = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, infoDatabase.getDatabaseName());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                InfoTable infoTable = new InfoTable();

                infoTable.setTableSchema(rs.getString("TABLE_SCHEMA"));
                infoTable.setTableName(rs.getString("TABLE_NAME"));

                // call method get info column from info table
                infoTable.setInfoColumns(getInfoColumns(infoTable, conn));
                // add info table to list info tables
                infoTables.add(infoTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return infoTables;
    }

    /**
     * Syntax of Oracle database get list info columns from input info Table
     * 
     * @param infoTable
     * @param conn
     * @return List info columns of Table
     */
    @Override
    public List<InfoColumn> getInfoColumns(InfoTable infoTable, Connection conn) {

        String sql = "SELECT TABLE_CATALOG, TABLE_NAME, COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG  = ? and TABLE_NAME = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<InfoColumn> infoColumns = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, infoTable.getTableSchema());
            pstmt.setString(2, infoTable.getTableName());

            rs = pstmt.executeQuery();
            while (rs.next()) {

                InfoColumn infoColumn = new InfoColumn();
                infoColumn.setTableSchema(rs.getString("TABLE_CATALOG"));
                infoColumn.setTableName(rs.getString("TABLE_NAME"));
                infoColumn.setColumnName(rs.getString("COLUMN_NAME"));
                infoColumn.setDataType(rs.getString("DATA_TYPE"));
                infoColumns.add(infoColumn);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return infoColumns;
    }

}
